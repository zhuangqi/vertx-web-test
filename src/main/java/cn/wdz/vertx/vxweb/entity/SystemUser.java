package cn.wdz.vertx.vxweb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.Date;
import java.util.Objects;

/**
 * 系统用户
 * @author wdz
 * @date 2019/12/2
 */
@DataObject(generateConverter = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SystemUser {

  private Long id;
  private String account;
  private String name;
  private String password;
  private Date gmtCreate;

  public SystemUser(JsonObject obj) {
    SystemUserConverter.fromJson(obj, this);
  }

  public SystemUser(String jsonStr) {
    SystemUserConverter.fromJson(new JsonObject(jsonStr), this);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @JsonIgnore
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Date getGmtCreate() {
    return gmtCreate;
  }

  public void setGmtCreate(Date gmtCreate) {
    this.gmtCreate = gmtCreate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof SystemUser)) return false;
    SystemUser user = (SystemUser) o;
    return Objects.equals(getId(), user.getId()) &&
      Objects.equals(getAccount(), user.getAccount()) &&
      Objects.equals(getName(), user.getName()) &&
      Objects.equals(getPassword(), user.getPassword()) &&
      Objects.equals(getGmtCreate(), user.getGmtCreate());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getAccount(), getName(), getPassword(), getGmtCreate());
  }
}
