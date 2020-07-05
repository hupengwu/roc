/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2012-2019. All rights reserved.
 */

package com.huawei.roc.cipher;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加密
 * 
 * @author h00442047
 * @since 2020年1月8日
 */
public class AESEncrypt {

    /**
     * 生成密码器：一次生成，反复使用，加密/解密大量数据，一次创建加密器，反复使用的方式，性能会高上两个数量级
     * 
     * @param opmode 加密模式Cipher.ENCRYPT_MODE； 解密模式Cipher.DECRYPT_MODE    
     * @param password 密码
     * @return 加密器
     */
    public static Cipher generateCipher(int opmode, String password) {
        try {
            // 创建AES的Key生产者
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            // 利用用户密码作为随机数初始化出 128位的key生产者
            kgen.init(128, new SecureRandom(password.getBytes()));
            // 加密没关系，SecureRandom是生成安全随机数序列，password.getBytes()是种子，只要种子相同，序列就一样，所以解密只要有password就行
            // 根据用户密码，生成一个密钥
            SecretKey secretKey = kgen.generateKey();
            // 返回基本编码格式的密钥，如果此密钥不支持编码，则返回null。
            byte[] enCodeFormat = secretKey.getEncoded();
            // 转换为AES专用密钥
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            // 创建密码器
            Cipher cipher = Cipher.getInstance("AES");
            // 初始化为加密模式的密码器
            cipher.init(opmode, key);

            return cipher;
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * AES加密字符串
     * 
     * @param byteContent 需要被加密的字符串
     * @param password 加密需要的密码
     * @return 密文
     */
    public static byte[] encrypt(byte[] byteContent, String password) {
        try {
            // 初始化为加密模式的密码器
            Cipher cipher = generateCipher(Cipher.ENCRYPT_MODE, password);
            // 加密
            byte[] result = cipher.doFinal(byteContent);

            return result;
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用加密器进行加密
     * 
     * @param byteContent 待加密的数据
     * @param cipher 加密器
     * @return 加密后的内容
     */
    public static byte[] encrypt(byte[] byteContent, Cipher cipher) {
        try {
            return cipher.doFinal(byteContent);
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 使用加密器进行解密：加密/解密大量数据，一次创建加密器，反复使用的方式，性能会高上两个数量级
     * 
     * @param content 文本内容
     * @param cipher 加密器
     * @return 加密后的数据
     */
    public static byte[] encryptString(String content, Cipher cipher) {
        try {
            return encrypt(content.getBytes("utf-8"), cipher);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 动态生成一个加密器进行加密
     * 
     * @param content 待加密的内容
     * @param password 密码
     * @return 加密后的数据
     */
    public static byte[] encryptString(String content, String password) {
        try {
            byte[] byteContent = content.getBytes("utf-8");
            return encrypt(byteContent, password);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 解密AES加密过的字符串
     * 
     * @param content AES加密过过的内容
     * @param password 加密时的密码
     * @return 明文
     */
    public static byte[] decrypt(byte[] content, String password) {
        try {
            // 初始化为解密模式的密码器
            Cipher cipher = generateCipher(Cipher.DECRYPT_MODE, password);
            // 解密
            byte[] result = cipher.doFinal(content);
            // 明文
            return result;

        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密成字符串：加密/解密大量数据，一次创建加密器，反复使用的方式，性能会高上两个数量级
     * 
     * @param content
     * @param cipher
     * @return
     */
    public static String decryptString(byte[] content, Cipher cipher) {
        try {
            // 解密
            byte[] decrypt = cipher.doFinal(content);
            // 明文
            return new String(decrypt);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密AES加密过的字符串
     * 
     * @param content AES加密过过的内容
     * @param password 加密时的密码
     * @return 明文
     */
    public static String decryptString(byte[] content, String password) {
        byte[] decrypt = decrypt(content, password);
        return new String(decrypt);
    }
}
