package cn.wdz.vertx.web.security;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.auth.User;

/**
 * 系统用户认证
 * @author wdz
 * @date 2019/12/16
 */
public class SysAuthPovider implements AuthProvider {

  @Override
  public void authenticate(JsonObject authInfo, Handler<AsyncResult<User>> resultHandler) {
    String account = authInfo.getString("account");
    String password = authInfo.getString("password");
    resultHandler.handle(Future.succeededFuture());
  }


}
