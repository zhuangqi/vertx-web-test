package cn.wdz.vertx.vxweb.service;

import cn.wdz.vertx.vxweb.entity.SystemUser;
import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * 系统用户接口
 * @author wdz
 * @date 2019/12/2
 */
public interface SystemUserService {

  Single<SystemUser> insert(SystemUser user);

  Maybe<SystemUser> get(Long id);

  Maybe<SystemUser> getByAccount(String account);
}
