package org.wjy.easycode.util.security;

/**
 * sha256
 * 
 * @author weijiayu
 * @date 2022/1/18 17:31
 */
public class EncryptBySha256 implements IEncrypt {

    @Override
    public String encrypt(String src) {
        return MessageDigestUtil.getSHA256(src);
    }

    @Override
    public boolean validatePassword(String target, String src) {
        return target.equals(MessageDigestUtil.getSHA256(src));
    }
}
