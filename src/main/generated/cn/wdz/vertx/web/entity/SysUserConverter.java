package cn.wdz.vertx.web.entity;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter for {@link cn.wdz.vertx.web.entity.SysUser}.
 * NOTE: This class has been automatically generated from the {@link cn.wdz.vertx.web.entity.SysUser} original class using Vert.x codegen.
 */
public class SysUserConverter {

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, SysUser obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "account":
          if (member.getValue() instanceof String) {
            obj.setAccount((String)member.getValue());
          }
          break;
        case "avatar":
          if (member.getValue() instanceof String) {
            obj.setAvatar((String)member.getValue());
          }
          break;
        case "delFlag":
          if (member.getValue() instanceof Boolean) {
            obj.setDelFlag((Boolean)member.getValue());
          }
          break;
        case "id":
          if (member.getValue() instanceof Number) {
            obj.setId(((Number)member.getValue()).longValue());
          }
          break;
        case "name":
          if (member.getValue() instanceof String) {
            obj.setName((String)member.getValue());
          }
          break;
        case "password":
          if (member.getValue() instanceof String) {
            obj.setPassword((String)member.getValue());
          }
          break;
        case "useFlag":
          if (member.getValue() instanceof Boolean) {
            obj.setUseFlag((Boolean)member.getValue());
          }
          break;
      }
    }
  }

  public static void toJson(SysUser obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(SysUser obj, java.util.Map<String, Object> json) {
    if (obj.getAccount() != null) {
      json.put("account", obj.getAccount());
    }
    if (obj.getAvatar() != null) {
      json.put("avatar", obj.getAvatar());
    }
    if (obj.getDelFlag() != null) {
      json.put("delFlag", obj.getDelFlag());
    }
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getName() != null) {
      json.put("name", obj.getName());
    }
    if (obj.getPassword() != null) {
      json.put("password", obj.getPassword());
    }
    if (obj.getUseFlag() != null) {
      json.put("useFlag", obj.getUseFlag());
    }
  }
}
