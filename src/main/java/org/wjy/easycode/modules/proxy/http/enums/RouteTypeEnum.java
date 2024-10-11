package org.wjy.easycode.modules.proxy.http.enums;

/**
 * 路由类型
 * 
 * @author weijiayu
 * @date 2024/1/23 9:48
 */
public enum RouteTypeEnum {

    LOCAL("0"), FORWARD("1");

    private String typeCode;

    RouteTypeEnum(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeCode() {
        return this.typeCode;
    }

    public static RouteTypeEnum getByTypeCode(String typeCode) {
        for (RouteTypeEnum v : RouteTypeEnum.values()) {
            if (v.typeCode.equals(typeCode)) {
                return v;
            }
        }
        return null;
    }
}
