package org.wjy.easycode.util.security;

import java.security.MessageDigest;

/**
 * 算法工具类
 * 
 * @author weijiayu
 * @date 2022/1/18 17:36
 */
public class MessageDigestUtil {

    private static char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String getMD5(String src) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(src.getBytes("UTF-8"));
            byte[] b = md.digest();
            return bufferToHex(b, 0, b.length);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static String getSHA256(String src) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(src.getBytes("UTF-8"));
            byte[] b = md.digest();
            return bufferToHex(b, 0, b.length);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static String bufferToHex(byte[] bytes, int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        // 取字节中高4位的数字转换
        stringbuffer.append(hexDigits[(bt & 0xf0) >> 4]);
        // 取字节中低4位的数字转换
        stringbuffer.append(hexDigits[bt & 0xf]);
    }

}
