package org.wjy.easycode.util.security;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * RSA算法工具类
 * 
 * @author weijiayu
 * @date 2022/1/19 14:26
 */
public class RsaUtil {

    private static KeyFactory getKeyFactory() throws NoSuchAlgorithmException {
        return KeyFactory.getInstance("RSA");
    }

    public static byte[] sign(SignatureSuite suite, byte[] msgBuf, String privateKeyStr)
        throws InvalidKeyException, SignatureException, InvalidKeySpecException, NoSuchAlgorithmException {
        Signature signature = Signature.getInstance(suite.val());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyStr));
        PrivateKey privateKey = getKeyFactory().generatePrivate(keySpec);
        signature.initSign(privateKey);
        signature.update(msgBuf);
        return signature.sign();
    }

    public static boolean verifySign(SignatureSuite suite, byte[] msgBuf, byte[] sign, String publicKeyStr)
        throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance(suite.val());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyStr));
        PublicKey publicKey = getKeyFactory().generatePublic(keySpec);
        signature.initVerify(publicKey);
        signature.update(msgBuf);
        return signature.verify(sign);
    }

    public static enum SignatureSuite {
        SHA1("SHA1WithRSA"), SHA256("SHA256WithRSA");

        private String suite;

        SignatureSuite(String suite) {
            this.suite = suite;
        }

        public String val() {
            return suite;
        }
    }
}
