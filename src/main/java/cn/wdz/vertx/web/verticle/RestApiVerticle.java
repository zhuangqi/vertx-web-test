package cn.wdz.vertx.web.verticle;

import cn.wdz.vertx.web.entity.SysUser;
import cn.wdz.vertx.web.handler.LoginHandler;
import cn.wdz.vertx.web.service.SysUserService;
import io.vertx.core.*;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.JWTAuthHandler;
import io.vertx.serviceproxy.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

/**
 * API服务Verticle
 * @author wdz
 * @date 2019/12/14
 */
public class RestApiVerticle extends AbstractVerticle {

  private final Logger log = LoggerFactory.getLogger(RestApiVerticle.class);

  @Override
  public void start(Promise<Void> startFuture) throws Exception {

    String host = config().getString("server.host","0.0.0.0");
    int port = config().getInteger("server.port", 8080);
    final Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());
    final SysUserService sysUserService = SysUserService.createProxy(vertx);

    // jwt
    JWTAuth provider = JWTAuth.create(vertx, new JWTAuthOptions()
      .addPubSecKey(new PubSecKeyOptions()
        .setAlgorithm("HS256")
        .setPublicKey("keyboard cat")
        .setSymmetric(true)));

    router.post("/auth/login").handler(new LoginHandler(sysUserService, provider));

    router.post("/sys/user").handler(context -> {
      SysUser sysUser = new SysUser(context.getBodyAsJson());
      sysUserService.create(sysUser, resultHandler(context, Json::encodePrettily));
    });

    router.get("/sys/user/:account").handler(JWTAuthHandler.create(provider)).handler(context -> {
      String account = context.request().getParam("account");
      sysUserService.getByAccount(account, resultHandler(context, Json::encodePrettily));
    });


    createHttpServer(router, host, port).onComplete(ar -> {
      if (ar.failed()) {
        log.error("Fail to start {}: {}", RestApiVerticle.class.getSimpleName(), ar.cause().getMessage());
        startFuture.fail(ar.cause());
      } else {
        log.info("\n----------------------------------------------------------\n\t" +
            "{} is running! Access URLs:\n\t" +
            "Local: \t\thttp://localhost:{}\n" +
            "----------------------------------------------------------",
          RestApiVerticle.class.getSimpleName(), port);
        startFuture.complete();
      }
    });
  }


  /**
   * Create http server for the REST service.
   *
   * @param router router instance
   * @param host   http host
   * @param port   http port
   * @return async result of the procedure
   */
  protected Future<Void> createHttpServer(Router router, String host, int port) {
    Promise<HttpServer> httpServerFuture = Promise.promise();
    vertx.createHttpServer()
      .requestHandler(router)
      .listen(port, host, httpServerFuture.future());
    return httpServerFuture.future().map(r -> null);
  }

  /**
   * This method generates handler for async methods in REST APIs.
   * Use the result directly and invoke `toString` as the response. The content type is JSON.
   */
  protected <T> Handler<AsyncResult<T>> resultHandler(RoutingContext context) {
    return ar -> {
      if (ar.succeeded()) {
        T res = ar.result();
        context.response()
          .putHeader("content-type", "application/json")
          .end(res == null ? "{}" : res.toString());
      } else {
        internalError(context, ar.cause());
        ar.cause().printStackTrace();
      }
    };
  }

  /**
   * This method generates handler for async methods in REST APIs.
   * Use the result directly and use given {@code converter} to convert result to string
   * as the response. The content type is JSON.
   *
   * @param context   routing context instance
   * @param converter a converter that converts result to a string
   * @param <T>       result type
   * @return generated handler
   */
  protected <T> Handler<AsyncResult<T>> resultHandler(RoutingContext context, Function<T, String> converter) {
    return ar -> {
      if (ar.succeeded()) {
        T res = ar.result();
        if (res == null) {
          serviceUnavailable(context, "invalid_result");
        } else {
          context.response()
            .putHeader("content-type", "application/json")
            .end(converter.apply(res));
        }
      } else {
        internalError(context, ar.cause());
        ar.cause().printStackTrace();
      }
    };
  }



  protected void serviceUnavailable(RoutingContext context, String cause) {
    context.response().setStatusCode(503)
      .putHeader("content-type", "application/json")
      .end(new JsonObject().put("error", cause).encodePrettily());
  }

  protected void internalError(RoutingContext context, Throwable ex) {
    if (ex instanceof ServiceException) {
      ServiceException exception = (ServiceException) ex;
      if (-1 != exception.failureCode()) {
        context.response().setStatusCode(exception.failureCode())
          .putHeader("content-type", "application/json")
          .end(new JsonObject().put("error", ex.getMessage()).encodePrettily());
        return;
      }
    }
    context.response().setStatusCode(500)
      .putHeader("content-type", "application/json")
      .end(new JsonObject().put("error", ex.getMessage()).encodePrettily());
  }

}
