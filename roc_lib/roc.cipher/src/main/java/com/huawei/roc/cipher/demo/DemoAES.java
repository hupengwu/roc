
package com.huawei.roc.cipher.demo;

import javax.crypto.Cipher;

import com.huawei.roc.cipher.AESEncrypt;

public class DemoAES {
    public static void main(String[] args) {
        String content = "美女，约吗？";
        String password = "123";
        System.out.println("加密之前：" + content);

        // 加密
        byte[] encrypt = AESEncrypt.encryptString(content, password);
        System.out.println("加密后的内容：" + new String(encrypt));

        // 解密
        String decrypt = AESEncrypt.decryptString(encrypt, password);
        System.out.println("解密后的内容：" + new String(decrypt));

        // 一次生成加密器，反复使用
        Cipher cipherEn = AESEncrypt.generateCipher(Cipher.ENCRYPT_MODE, password);
        Cipher cipherDe = AESEncrypt.generateCipher(Cipher.DECRYPT_MODE, password);

        content = "帅哥，约吗？";
        System.out.println("加密之前：" + content);
        encrypt = AESEncrypt.encryptString(content, cipherEn);
        System.out.println("加密后的内容：" + new String(encrypt));
        decrypt = AESEncrypt.decryptString(encrypt, cipherDe);
        System.out.println("解密后的内容：" + new String(decrypt));

        content = "妹子，约吗？";
        System.out.println("加密之前：" + content);
        encrypt = AESEncrypt.encryptString(content, cipherEn);
        System.out.println("加密后的内容：" + new String(encrypt));
        decrypt = AESEncrypt.decryptString(encrypt, cipherDe);
        System.out.println("解密后的内容：" + new String(decrypt));

        for (int i = 0; i < 1000000; i++) {
            content = "性能测试性能测试性能测试性能测试性能测试性能测试性能测试";
            encrypt = AESEncrypt.encryptString(content, cipherEn);
            decrypt = AESEncrypt.decryptString(encrypt, cipherDe);
        }
        
        System.out.println("解密后的内容：" + new String(decrypt));

    }

}
