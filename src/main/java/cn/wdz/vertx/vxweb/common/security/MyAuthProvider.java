package cn.wdz.vertx.vxweb.common.security;

import cn.wdz.vertx.vxweb.service.SystemUserServiceImpl;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.auth.User;
import io.vertx.ext.auth.impl.hash.SHA512;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.auth.HashingStrategy;
import io.vertx.reactivex.ext.jdbc.JDBCClient;
import io.vertx.reactivex.ext.web.Router;

/**
 * 自定义认证
 * @author wdz
 * @date 2019/12/4
 */
public class MyAuthProvider implements AuthProvider {

  protected final Vertx vertx;

  protected final Router router;

  protected final JDBCClient jdbcClient;

  public MyAuthProvider(Vertx vertx, Router router, JDBCClient jdbcClient) {
    this.vertx = vertx;
    this.router = router;
    this.jdbcClient = jdbcClient;
  }

  @Override
  public void authenticate(JsonObject authInfo, Handler<AsyncResult<User>> resultHandler) {
    String account = authInfo.getString("account");
    String password = authInfo.getString("password");

    new SystemUserServiceImpl(jdbcClient).getByAccount(account)
      .toSingle().subscribe(systemUser -> {
        HashingStrategy hashingStrategy = HashingStrategy.load();
        String signpwd = hashingStrategy.hash(new SHA512().id(),
          null, "vext-web", password);
        if (systemUser.getPassword().equals(signpwd)) {
          //密码验证通过
          User user = new MyUser(systemUser);
          resultHandler.handle(Future.succeededFuture(user));
        } else {
          //密码验证不通过
          resultHandler.handle(Future.failedFuture("用户名或密码错误"));
        }
    });


  }


}
