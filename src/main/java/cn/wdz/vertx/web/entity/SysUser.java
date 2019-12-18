package cn.wdz.vertx.web.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.time.LocalDateTime;

/**
 * @author wdz
 * @date 2019/12/14
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@DataObject(generateConverter = true)
public class SysUser {

  private Long id;
  private String name;
  private String account;
  private String password;
  private String avatar;
  private Boolean delFlag;
  private Boolean useFlag;
  private LocalDateTime gmtCreate;
  private LocalDateTime gmtUpdate;


  public SysUser() {
  }

  public SysUser(JsonObject obj) {
    SysUserConverter.fromJson(obj, this);
  }

  public JsonObject toJson() {
    JsonObject json = new JsonObject();
    SysUserConverter.toJson(this, json);
    return json;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  @JsonIgnore
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  @JsonIgnore
  public Boolean getDelFlag() {
    return delFlag;
  }

  public void setDelFlag(Boolean delFlag) {
    this.delFlag = delFlag;
  }

  @JsonIgnore
  public Boolean getUseFlag() {
    return useFlag;
  }

  public void setUseFlag(Boolean useFlag) {
    this.useFlag = useFlag;
  }

  public LocalDateTime getGmtCreate() {
    return gmtCreate;
  }

  public void setGmtCreate(LocalDateTime gmtCreate) {
    this.gmtCreate = gmtCreate;
  }

  public LocalDateTime getGmtUpdate() {
    return gmtUpdate;
  }

  public void setGmtUpdate(LocalDateTime gmtUpdate) {
    this.gmtUpdate = gmtUpdate;
  }
}
