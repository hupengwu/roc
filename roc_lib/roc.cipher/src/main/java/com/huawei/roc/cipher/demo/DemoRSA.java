
package com.huawei.roc.cipher.demo;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import com.huawei.roc.cipher.RSAEncrypt;

public class DemoRSA {
    public static void main(String[] args) {
        try {
            KeyPair keyPair = RSAEncrypt.genKeyPair();
            // 得到私钥
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            // 得到公钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

            byte[] cipherData = RSAEncrypt.encrypt(publicKey, "明文".getBytes());
            byte[] plainTextData = RSAEncrypt.decrypt(privateKey, cipherData);
            String result = new String(plainTextData);
            result.getBytes();
        } catch (Exception e) {
            e.toString();
        }

    }
}
