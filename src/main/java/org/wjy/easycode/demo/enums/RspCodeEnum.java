package org.wjy.easycode.demo.enums;


import org.wjy.easycode.demo.service.IRspCode;

public enum RspCodeEnum implements IRspCode {

    RSP_SUCCESS("0","success");

    private String code;
    private String msg;

    RspCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    // 反向查找
    public static RspCodeEnum getByCode(String code) {
        for (RspCodeEnum rspCodeEnum : values()) {
            if (rspCodeEnum.code.equals(code)) {
                return rspCodeEnum;
            }
        }
        return null;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
