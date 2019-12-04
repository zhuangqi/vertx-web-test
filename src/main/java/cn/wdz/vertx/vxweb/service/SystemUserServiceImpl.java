package cn.wdz.vertx.vxweb.service;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.wdz.vertx.vxweb.entity.SystemUser;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.auth.impl.hash.SHA512;
import io.vertx.ext.sql.ResultSet;
import io.vertx.reactivex.ext.auth.HashingStrategy;
import io.vertx.reactivex.ext.jdbc.JDBCClient;

import java.util.Date;

/**
 * @author wdz
 * @date 2019/12/2
 */
public class SystemUserServiceImpl implements SystemUserService {

  private JDBCClient client;

  public SystemUserServiceImpl(JDBCClient client) {
    this.client = client;
  }

  @Override
  public Single<SystemUser> insert(SystemUser user) {
    HashingStrategy hashingStrategy = HashingStrategy.load();
    user.setPassword(hashingStrategy.hash(new SHA512().id(),
      null, "vext-web", user.getPassword()));
    user.setGmtCreate(new Date());
    JsonArray params = new JsonArray()
      .add(user.getAccount())
      .add(user.getPassword())
      .add(user.getName())
      .add(DateUtil.format(user.getGmtCreate(), DatePattern.UTC_FORMAT));
    return client.rxUpdateWithParams(SQL_CREATE,params)
      .map(e -> {
        user.setId(e.getKeys().getLong(0));
        return user;
      });
  }

  @Override
  public Maybe<SystemUser> get(Long id) {
    return client.rxQueryWithParams(SQL_QUERY, new JsonArray().add(id))
      .map(ResultSet::getRows)
      .toObservable()
      .flatMapIterable(e -> e)
      .singleElement()
      .map(SystemUser::new);
  }


  private static final String SQL_CREATE = "INSERT INTO `sys_user` " +
    "(`account`, `password`,`name`,`gmt_create`) VALUES (?, ?, ?, ?)";

  private static final String SQL_QUERY = "select * from sys_user where id = ?";

}
