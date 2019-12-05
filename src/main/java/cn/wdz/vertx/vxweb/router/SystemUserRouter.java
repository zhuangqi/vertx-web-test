package cn.wdz.vertx.vxweb.router;

import cn.wdz.vertx.vxweb.common.server.ServerContext;
import cn.wdz.vertx.vxweb.entity.SystemUser;
import cn.wdz.vertx.vxweb.service.SystemUserService;
import cn.wdz.vertx.vxweb.service.SystemUserServiceImpl;
import io.reactivex.Maybe;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.core.eventbus.EventBus;
import io.vertx.reactivex.ext.jdbc.JDBCClient;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.RoutingContext;

import java.util.Objects;
import java.util.Optional;

/**
 * @author wdz
 * @date 2019/12/2
 */
public class SystemUserRouter extends ServerContext {

  private SystemUserService systemUserService;

  public SystemUserRouter(Vertx vertx, Router router, JDBCClient client) {
    super(vertx, router, client);
    this.systemUserService = new SystemUserServiceImpl(client);
  }

  @Override
  public void loadRouter() {
    router.post("/sys/user").handler(this::handleCreate);
    router.post("/sys/user/:id").handler(this::handleGet);

    EventBus eventBus = vertx.eventBus();
    eventBus.<String>consumer("get_sysuser_account", msg -> {
      String account = msg.body();
      systemUserService.getByAccount(account)
        .map(Optional::of)
        .switchIfEmpty(Maybe.just(Optional.empty()))
        .toSingle()
        .subscribe(r -> {
          if (r.isPresent()) {
            msg.reply(r.get());
          } else {
            msg.fail(404,"用户不存在");
          }
        });
    });



  }

  private void handleCreate(RoutingContext context) {
    try {
      JsonObject json = context.getBodyAsJson();
      if (Objects.isNull(json)) {
        badRequest(context);
        return;
      }
      SystemUser user = new SystemUser(json);
      sendResponse(context, systemUserService.insert(user), Json::encodePrettily,this::created);

    } catch (DecodeException ex) {
      badRequest(context, ex);
    }
  }

  private void handleGet(RoutingContext context) {
    String id = context.request().getParam("id");
    if (Objects.isNull(id)) {
      badRequest(context);
    }
    sendResponse(context, systemUserService.get(Long.valueOf(id)), Json::encodePrettily);
  }

}
