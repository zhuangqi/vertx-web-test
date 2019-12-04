package cn.wdz.vertx.vxweb.entity;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter for {@link cn.wdz.vertx.vxweb.entity.SystemUser}.
 * NOTE: This class has been automatically generated from the {@link cn.wdz.vertx.vxweb.entity.SystemUser} original class using Vert.x codegen.
 */
public class SystemUserConverter {

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, SystemUser obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "account":
          if (member.getValue() instanceof String) {
            obj.setAccount((String)member.getValue());
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
      }
    }
  }

  public static void toJson(SystemUser obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(SystemUser obj, java.util.Map<String, Object> json) {
    if (obj.getAccount() != null) {
      json.put("account", obj.getAccount());
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
  }
}
