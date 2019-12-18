package cn.wdz.vertx.web.service;

import cn.wdz.vertx.web.entity.SysUser;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

/**
 * 系统用户接口
 * @author wdz
 * @date 2019/12/14
 */
@VertxGen
@ProxyGen
public interface SysUserService {


  String SERVICE_ADDRESS = "service.sys.user";

  /**
   * 创建系统用户
   * @param user 用户参数
   * @param handler 异步处理handler
   */
  void create(SysUser user, Handler<AsyncResult<SysUser>> handler);

  /**
   * 修改系统用户
   * @param user 用户参数
   * @param handler 异步处理handler
   */
  void update(SysUser user, Handler<AsyncResult<SysUser>> handler);

  /**
   * 根据账号获取
   * @param account 账号
   * @param handler 异步处理handler
   */
  void getByAccount(String account, Handler<AsyncResult<SysUser>> handler);


  /**
   * 创建服务代理
   * @param vertx vertx
   * @return 当前服务代理
   */
  static SysUserService createProxy(Vertx vertx) {
    return new SysUserServiceVertxEBProxy(vertx,SERVICE_ADDRESS);
  }

}
