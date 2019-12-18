package cn.wdz.vertx.web.handler;

import cn.wdz.vertx.web.service.SysUserService;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.jwt.JWTOptions;
import io.vertx.ext.web.RoutingContext;

/**
 * @author wdz
 * @date 2019/12/16
 */
public class LoginHandler implements Handler<RoutingContext> {

  private final SysUserService sysUserService;

  private final JWTAuth provider;

  public LoginHandler(SysUserService sysUserService, JWTAuth provider) {
    this.sysUserService = sysUserService;
    this.provider = provider;
  }

  @Override
  public void handle(RoutingContext context) {
    JsonObject jsonObject = context.getBodyAsJson();
    String account = jsonObject.getString("account");
    String password = jsonObject.getString("password");
    sysUserService.getByAccount(account, ar -> {
      if (ar.succeeded()) {
        if (null == ar.result()) {
          unauthorized(context);
        }
        if (password.equals(ar.result().getPassword())) {
          String token = provider.generateToken(new JsonObject().put("userid", ar.result().getId()), new JWTOptions());
          context.response()
            .putHeader("content-type", "application/json")
            .end(token);
        } else {
          unauthorized(context);
        }
      } else {
        internalError(context, ar.cause());
      }
    });
  }

  private void unauthorized(RoutingContext context) {
    context.response().setStatusCode(401)
      .putHeader("content-type", "application/json")
      .end("账号或密码错误");
    return;
  }

  private void internalError(RoutingContext context, Throwable ex) {
    context.response().setStatusCode(500)
      .putHeader("content-type", "application/json")
      .end(new JsonObject().put("error", ex.getMessage()).encodePrettily());
  }


}
