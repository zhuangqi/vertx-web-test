package cn.wdz.vertx.vxweb;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.wdz.vertx.vxweb.common.server.RouterDefinition;
import io.reactivex.Completable;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.ext.jdbc.JDBCClient;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.handler.BodyHandler;
import io.vertx.reactivex.ext.web.handler.CorsHandler;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class MainVerticle extends AbstractVerticle {

  private static Logger logger = LoggerFactory.getLogger(MainVerticle.class);

  @Override
  public void start(Promise<Void> startFuture) throws Exception {
    Router router = Router.router(vertx);
    enableCorsSupport(router);
    router.route().handler(BodyHandler.create());
    router.errorHandler(500, context -> logger.error("🤣", context.failure()));

    loadRouter(router).andThen(createHttpServer(router, "127.0.0.1",8080))
      .subscribe(startFuture::complete, startFuture::fail);
  }

  /**
   * 装载路由
   * @param router
   * @return
   */
  private Completable loadRouter(Router router) {
    return Completable.create(emitter -> {
      final String basePackage = "cn.wdz.vertx.vxweb";
      Set<Class<?>> routers = ClassUtil.
        scanPackageBySuper(basePackage, RouterDefinition.class);
      if (Objects.isNull(routers)) {
        emitter.onComplete();
      }
      routers.forEach(r -> {
        RouterDefinition routerImpl = (RouterDefinition) ReflectUtil
          .newInstance(r,vertx,router, JDBCClient.createShared(vertx, config()));
        routerImpl.loadRouter();
      });
      emitter.onComplete();
    });
  }

  /**
   * Enable CORS support for web router.
   *
   * @param router router instance
   */
  private void enableCorsSupport(Router router) {
    Set<String> allowHeaders = new HashSet<>();
    allowHeaders.add("x-requested-with");
    allowHeaders.add("Access-Control-Allow-Origin");
    allowHeaders.add("origin");
    allowHeaders.add("Content-Type");
    allowHeaders.add("accept");
    // CORS support
    router.route().handler(CorsHandler.create("*")
      .allowedHeaders(allowHeaders)
      .allowedMethod(HttpMethod.GET)
      .allowedMethod(HttpMethod.POST)
      .allowedMethod(HttpMethod.DELETE)
      .allowedMethod(HttpMethod.PATCH)
      .allowedMethod(HttpMethod.PUT)
    );
  }

  /**
   * 创建http服务
   * @param router
   * @param host
   * @param port
   * @return
   */
  private Completable createHttpServer(Router router, String host, int port) {
    return vertx.createHttpServer()
      .requestHandler(router)
      .rxListen(port, host)
      .ignoreElement();
  }


}
