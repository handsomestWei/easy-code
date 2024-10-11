package org.wjy.easycode.util.security;

/**
 * 加解密接口
 * 
 * @author weijiayu
 * @date 2022/1/18 17:30
 */
public interface IEncrypt {

    /**
     * 加密
     * 
     * @author weijiayu
     * @date 2022/1/18 17:30
     * @param src
     * @return java.lang.String
     */
    public String encrypt(String src);

    /**
     * 校验密码
     * 
     * @author weijiayu
     * @date 2022/1/18 17:31
     * @param target
     * @param src
     * @return boolean
     */
    public boolean validatePassword(String target, String src);
}
