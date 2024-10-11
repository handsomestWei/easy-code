package org.wjy.easycode.util.security;

import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * AES加解密
 * 
 * @author weijiayu
 * @date 2022/1/18 17:23
 */
public class AesUtil {

    private static Cipher cipher = null;

    /**
     * 获取加密算法对象
     * 
     * @author weijiayu
     * @date 2022/1/18 17:23
     * @return javax.crypto.Cipher
     */
    private static Cipher getCipher() throws NoSuchAlgorithmException, NoSuchPaddingException {
        cipher = Cipher.getInstance("AES/CFB/PKCS5Padding");
        return cipher;
    }

    /**
     * 使用AES加密原文
     * 
     * @author weijiayu
     * @date 2022/1/18 17:26
     * @param sSrc,
     * @param sKey
     * @param ivParameter
     * @return byte[]
     */
    public static byte[] encryptWithBase64(byte[] sSrc, String sKey, String ivParameter) throws Exception {
        ensureSrcNotNull(sSrc);
        ensureKeyIsRight(sKey);
        byte[] raw = sKey.getBytes("UTF8");
        SecretKeySpec sKeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = getCipher();
        // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes(Charset.forName("UTF8")));
        cipher.init(Cipher.ENCRYPT_MODE, sKeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc);
        return Base64.encodeBase64(encrypted);
    }

    /**
     * 使用AES算法解密
     * 
     * @author weijiayu
     * @date 2022/1/18 17:26
     * @param cipherText
     * @param secretKey
     * @return java.lang.String
     */
    public static String decrypt(String cipherText, String secretKey) throws Exception {
        byte[] sSrc = cipherText.getBytes("UTF-8");
        ensureSrcNotNull(sSrc);
        ensureKeyIsRight(secretKey);
        byte[] raw = secretKey.getBytes("UTF8");
        SecretKeySpec sKeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = getCipher();
        // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        IvParameterSpec iv = new IvParameterSpec(EncryptUtil.encryptFunc(secretKey).substring(0, 16).getBytes("UTF-8"));
        cipher.init(Cipher.DECRYPT_MODE, sKeySpec, iv);
        byte[] encrypted = new Base64().decode(sSrc);
        byte[] original = cipher.doFinal(encrypted);
        return new String(original, "UTF-8");
    }

    private static void ensureSrcNotNull(byte[] sSrc) {
        if (sSrc == null) {
            throw new IllegalArgumentException("src is empty.");
        }
    }

    private static void ensureKeyIsRight(String key) {
        if (key == null) {
            throw new IllegalArgumentException("key is empty.");
        }
        if (key.length() != 16) {
            throw new IllegalArgumentException("key len is not 16");
        }
    }
}
