package cn.wdz.vertx.vxweb.common.server;

import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.jdbc.JDBCClient;
import io.vertx.reactivex.ext.web.Router;

/**
 * @author wdz
 * @date 2019/12/2
 */
public class ServerContext extends ServerResponse implements RouterDefinition {

  protected final Vertx vertx;

  protected final Router router;

  private JDBCClient client;

  public ServerContext(Vertx vertx, Router router, JDBCClient client) {
    this.vertx = vertx;
    this.router = router;
    this.client = client;
  }

  @Override
  public void loadRouter() {

  }


}
