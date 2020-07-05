
package com.huawei.roc.cipher;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * MD5加密/验证：通过MD5签名，防止明文和明文比较，密文和密文比较会被攻击者篡改的安全性问题，达到密码的黑盒的效果
 * 背景：MD5加密生成出来的，是一段校验值，并不包含密码本身。这个校验值存储起来，并不涉及密码存储。
 * 
 * https://www.cnblogs.com/tingbogiu/p/5916355.html
 * 
 * @author h00442047
 * @since 2020年1月10日
 */
public class Md5Encrypt {

    private static final String HEX_NUMS_STR = "0123456789ABCDEF";

    /**
     * 盐的长度
     */
    private static final Integer SALT_LENGTH = 12;

    /**   
     * 将16进制字符串转换成字节数组   
     * @param hex   
     * @return   
     */
    private static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] hexChars = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (HEX_NUMS_STR.indexOf(hexChars[pos]) << 4 | HEX_NUMS_STR.indexOf(hexChars[pos + 1]));
        }
        return result;
    }

    /**  
     * 将指定byte数组转换成16进制字符串  
     * @param b  
     * @return  
     */
    private static String byteToHexString(byte[] b) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            hexString.append(hex.toUpperCase());
        }
        return hexString.toString();
    }

    /**  
     * 验证口令是否合法  
     * @param password  
     * @param passwordInDb  
     * @return  
     * @throws NoSuchAlgorithmException  
     */
    public static boolean valid(byte[] password, byte[] pwdInDb, int saltLength) throws NoSuchAlgorithmException {
        // 声明盐变量
        byte[] salt = new byte[saltLength];
        // 将盐从数据库中保存的口令字节数组中提取出来
        System.arraycopy(pwdInDb, 0, salt, 0, saltLength);
        // 创建消息摘要对象
        MessageDigest md = MessageDigest.getInstance("MD5");
        // 将盐数据传入消息摘要对象
        md.update(salt);
        // 将口令的数据传给消息摘要对象
        md.update(password);
        // 生成输入口令的消息摘要
        byte[] digest = md.digest();
        // 声明一个保存数据库中口令消息摘要的变量
        byte[] digestInDb = new byte[pwdInDb.length - saltLength];
        // 取得数据库中口令的消息摘要
        System.arraycopy(pwdInDb, saltLength, digestInDb, 0, digestInDb.length);
        // 比较根据输入口令生成的消息摘要和数据库中消息摘要是否相同
        if (Arrays.equals(digest, digestInDb)) {
            // 口令正确返回口令匹配消息
            return true;
        } else {
            // 口令不正确返回口令不匹配消息
            return false;
        }
    }

    /**  
     * 验证口令是否合法  
     * @param password  
     * @param passwordInDb  
     * @return  
     * @throws NoSuchAlgorithmException  
     * @throws UnsupportedEncodingException  
     */
    public static boolean valid(String password,
        String passwordInDb) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // 将16进制字符串格式口令转换成字节数组
        byte[] pwdInDb = hexStringToByte(passwordInDb);

        return valid(password.getBytes("UTF-8"), pwdInDb, SALT_LENGTH);
    }

    /**  
     * 获得加密后的16进制形式口令  
     * @param password  
     * @return  
     * @throws NoSuchAlgorithmException  
     * @throws UnsupportedEncodingException  
     */
    public static byte[] encrypt(byte[] password, int saltLength) throws NoSuchAlgorithmException {
        // 声明加密后的口令数组变量
        byte[] pwd = null;
        // 随机数生成器
        SecureRandom random = new SecureRandom();
        // 声明盐数组变量 12
        byte[] salt = new byte[saltLength];
        // 将随机数放入盐变量中
        random.nextBytes(salt);

        // 声明消息摘要对象
        MessageDigest md = null;
        // 创建消息摘要
        md = MessageDigest.getInstance("MD5");
        // 将盐数据传入消息摘要对象
        md.update(salt);
        // 将口令的数据传给消息摘要对象
        md.update(password);
        // 获得消息摘要的字节数组
        byte[] digest = md.digest();

        // 因为要在口令的字节数组中存放盐，所以加上盐的字节长度
        pwd = new byte[digest.length + saltLength];
        // 将盐的字节拷贝到生成的加密口令字节数组的前12个字节，以便在验证口令时取出盐
        System.arraycopy(salt, 0, pwd, 0, saltLength);
        // 将消息摘要拷贝到加密口令字节数组从第13个字节开始的字节
        System.arraycopy(digest, 0, pwd, saltLength, digest.length);

        return pwd;
    }

    /**  
     * 获得加密后的16进制形式口令  
     * @param password  
     * @return  
     * @throws NoSuchAlgorithmException  
     * @throws UnsupportedEncodingException  
     */
    public static String encrypt(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        // 声明加密后的口令数组变量
        byte[] pwd = encrypt(password.getBytes("UTF-8"), SALT_LENGTH);
        // 将字节数组格式加密后的口令转化为16进制字符串格式的口令
        return byteToHexString(pwd);
    }
}