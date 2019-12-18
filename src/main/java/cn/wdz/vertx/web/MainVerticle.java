package cn.wdz.vertx.web;

import cn.wdz.vertx.web.service.wrapper.JdbcRepositoryWrapper;
import cn.wdz.vertx.web.verticle.RbacVerticle;
import cn.wdz.vertx.web.verticle.RestApiVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.reactivex.core.AbstractVerticle;

/**
 * 主Verticle、用来部署其他Verticle
 */
public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Future<Void> startFuture) throws Exception {

    JdbcRepositoryWrapper jdbc = new JdbcRepositoryWrapper(vertx, config());

    vertx.deployVerticle(new RestApiVerticle(),new DeploymentOptions().setConfig(config()),
      ar -> vertx.deployVerticle(new RbacVerticle(jdbc),new DeploymentOptions().setConfig(config())));

  }


}
