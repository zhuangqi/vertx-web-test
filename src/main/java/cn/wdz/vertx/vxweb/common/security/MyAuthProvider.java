package cn.wdz.vertx.vxweb.common.security;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.auth.User;

/**
 * 自定义认证
 * @author wdz
 * @date 2019/12/4
 */
public class MyAuthProvider implements AuthProvider {

  @Override
  public void authenticate(JsonObject authInfo, Handler<AsyncResult<User>> resultHandler) {
    String account = authInfo.getString("account");
    String password = authInfo.getString("password");

  }


}
