package org.wjy.easycode.util.security;

/**
 * 加密工具类
 *
 * @author weijiayu
 * @date 2022/1/18 17:33
 */
public class EncryptUtil {

    public static final int SHA256MODE = 0;
    public static final int MD5MODE = 1;

    /**
     * 加密
     * 
     * @author weijiayu
     * @date 2022/1/18 17:36
     * @param src
     * @param model
     * @return java.lang.String
     */
    public static String encryptFunc(String src, int model) {
        IEncrypt encrypt = getEncryptInstance(model);
        return encrypt.encrypt(src);
    }

    /**
     * 加密,默认加密模式为SHA256
     * 
     * @author weijiayu
     * @date 2022/1/18 17:35
     * @param src
     * @return java.lang.String
     */
    public static String encryptFunc(String src) {
        IEncrypt encrypt = getEncryptInstance(SHA256MODE);
        return encrypt.encrypt(src);
    }

    /**
     * 密码校验
     * 
     * @author weijiayu
     * @date 2022/1/18 17:34
     * @param target
     * @param src
     * @param model
     * @return boolean
     */
    public static boolean validatePasswordFunc(String target, String src, int model) {
        IEncrypt encrypt = getEncryptInstance(model);
        return encrypt.validatePassword(target, src);
    }

    /**
     * 加密模式
     * 
     * @author weijiayu
     * @date 2022/1/18 17:34
     * @param encryptionMode
     * @return com.hikvision.gx.wutool.util.security.IEncrypt
     */
    private static IEncrypt getEncryptInstance(int encryptionMode) {
        IEncrypt iEncrypt;
        switch (encryptionMode) {
            case MD5MODE:
                iEncrypt = new EncryptByMd5();
                break;
            case SHA256MODE:
            default:
                iEncrypt = new EncryptBySha256();
                break;
        }
        return iEncrypt;
    }
}
