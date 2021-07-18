package org.wjy.easycode.util;

import java.io.IOException;

/**
 * 有时图片base64数据decode还原后，无法正常显示，可以尝试改用其他方法尝试
 */
public class Base64Helper {

    // 使用jdk自带的方法
    public static byte[] decodeByJDK(String data) {
        return java.util.Base64.getDecoder().decode(data);
    }

    // 使用apache提供的方法，来自于sun.misc.BASE64Decoder()
    public static byte[] decodeByApache(String data) {
        return org.apache.commons.codec.binary.Base64.decodeBase64(data);
    }

    // 不建议使用，jdk8以后已经移除该方法，在高版本jdk环境下会找不到方法而报错
    @Deprecated
    public static byte[] decodeBySun(String data) throws IOException {
        return new sun.misc.BASE64Decoder().decodeBuffer(data);
    }
}
