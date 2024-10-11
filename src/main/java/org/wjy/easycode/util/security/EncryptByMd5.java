package org.wjy.easycode.util.security;

/**
 * md5
 *
 * @author weijiayu
 * @date 2022/1/18 17:29
 */
public class EncryptByMd5 implements IEncrypt {

    @Override
    public String encrypt(String src) {
        return MessageDigestUtil.getMD5(src);
    }

    @Override
    public boolean validatePassword(String target, String src) {
        return target.equals(MessageDigestUtil.getMD5(src));
    }
}
