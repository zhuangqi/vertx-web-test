package cn.wdz.vertx.web.verticle;

import cn.wdz.vertx.web.service.SysUserService;
import cn.wdz.vertx.web.service.impl.SysUserServiceImpl;
import cn.wdz.vertx.web.service.wrapper.JdbcRepositoryWrapper;
import io.vertx.core.AbstractVerticle;
import io.vertx.serviceproxy.ServiceBinder;

/**
 * RBAC权限Verticle
 * @author wdz
 * @date 2019/12/14
 */
public class RbacVerticle extends AbstractVerticle {

  private final JdbcRepositoryWrapper jdbcRepositoryWrapper;

  public RbacVerticle(JdbcRepositoryWrapper jdbcRepositoryWrapper) {
    this.jdbcRepositoryWrapper = jdbcRepositoryWrapper;
  }

  @Override
  public void start() throws Exception {
    SysUserService sysUserService = new SysUserServiceImpl(jdbcRepositoryWrapper);
    new ServiceBinder(vertx).setAddress(SysUserService.SERVICE_ADDRESS).register(SysUserService.class, sysUserService);
  }


}
