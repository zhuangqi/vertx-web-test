package cn.wdz.vertx.web.service.impl;

import cn.wdz.vertx.web.entity.SysUser;
import cn.wdz.vertx.web.service.SysUserService;
import cn.wdz.vertx.web.service.wrapper.JdbcRepositoryWrapper;
import io.reactivex.Maybe;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.auth.HashingStrategy;
import io.vertx.ext.auth.impl.hash.SHA512;
import io.vertx.ext.sql.ResultSet;

import java.util.Optional;

/**
 * @author wdz
 * @date 2019/12/14
 */
public class SysUserServiceImpl implements SysUserService {

  private final JdbcRepositoryWrapper jdbc;

  public SysUserServiceImpl(JdbcRepositoryWrapper jdbcRepositoryWrapper) {
    this.jdbc = jdbcRepositoryWrapper;
  }

  @Override
  public void create(SysUser user, Handler<AsyncResult<SysUser>> handler) {

  }

  @Override
  public void update(SysUser user, Handler<AsyncResult<SysUser>> handler) {

  }

  @Override
  public void getByAccount(String account, Handler<AsyncResult<SysUser>> handler) {
    jdbc.getClient().rxQueryWithParams(FETCH_BY_ACCOUNT_STATEMENT, new JsonArray().add(account))
      .map(ResultSet::getRows)
      .toObservable()
      .flatMapIterable(e -> e)
      .singleElement()
      .map(SysUser::new)
      .map(Optional::of)
      .switchIfEmpty(Maybe.just(Optional.empty()))
      .toSingle()
      .subscribe(
        r -> {
          if (r.isPresent()) {
            handler.handle(Future.succeededFuture(r.get()));
          } else {
            handler.handle(Future.succeededFuture());
          }
        },
        ex ->  handler.handle(Future.failedFuture(ex))
        );
  }


  // password encrypt

  private String encryptPwd(String password) {
    HashingStrategy hashingStrategy = HashingStrategy.load();
    return hashingStrategy.hash(new SHA512().id(),null, "wdz", password);
  }




  // SQL statement

  private static final String INSERT_STATEMENT = "INSERT INTO sys_user(name, account, password, del_flag, use_flag, gmt_create) VALUES (?, ?, ?, ?, ?, ?)";

  private static final String EXISTS_STATEMENT = "SELECT COUNT(1) FROM sys_user WHERE account = ?";

  private static final String FETCH_BY_ACCOUNT_STATEMENT = "SELECT * FROM sys_user where account = ?";

}
