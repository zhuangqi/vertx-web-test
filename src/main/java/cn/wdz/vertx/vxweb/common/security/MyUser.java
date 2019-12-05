package cn.wdz.vertx.vxweb.common.security;

import cn.wdz.vertx.vxweb.entity.SystemUser;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.auth.User;

/**
 * @author wdz
 * @date 2019/12/5
 */
public class MyUser implements User {

  private final SystemUser user;

  public MyUser(SystemUser user) {
    this.user = user;
  }

  @Override
  public User isAuthorized(String authority, Handler<AsyncResult<Boolean>> resultHandler) {
    resultHandler.handle(Future.succeededFuture(true));
    return this;
  }

  @Override
  public User clearCache() {
    return null;
  }

  @Override
  public JsonObject principal() {
    return user.toJson();
  }

  @Override
  public void setAuthProvider(AuthProvider authProvider) {

  }

}
