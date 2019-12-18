package cn.wdz.vertx.web.service.wrapper;

import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.jdbc.JDBCClient;

/**
 * @author wdz
 * @date 2019/12/14
 */
public class JdbcRepositoryWrapper {

  protected final JDBCClient client;

  public JdbcRepositoryWrapper(Vertx vertx, JsonObject config) {
    this.client = JDBCClient.createShared(vertx, config);
  }

  public JDBCClient getClient() {
    return client;
  }
}
