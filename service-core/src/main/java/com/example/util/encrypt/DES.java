package com.example.util.encrypt;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

/**
 * @title: DES
 * @Description: DES加解密
 * @author: roverxiafan
 * @date: 2016/11/7 15:56
 */
@Slf4j
public class DES {
    private static final String KEY_ALGORITHM = "DES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "DES/ECB/PKCS5Padding";

    /**
     * DES 加密操作
     *
     * @param content 待加密内容
     * @param password 加密密码
     * @return 返回Base64转码后的加密数据
     */
    public static String encrypt(String content, String password) {
        try{
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(password), new SecureRandom());
            return Base64.encodeBase64String(cipher.doFinal(content.getBytes())); //按单部分操作加密或解密数据，或者结束一个多部分操作
        }catch(Exception e){
            log.error("DES encrypt exception", e);
        }
        return null;
    }

    /**
     * DES 解密操作
     *
     * @param content Base64转码后的加密数据
     * @param password 解密密码
     * @return 解密内容
     */
    public static String decrypt(String content, String password) {
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(password), new SecureRandom());
            return new String(cipher.doFinal(Base64.decodeBase64(content)), "utf-8");
        } catch (Exception e) {
            log.error("DES decrypt exception", e);
        }

        return null;
    }

    private static Key getSecretKey(final String password) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
        DESKeySpec des = new DESKeySpec(password.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        return keyFactory.generateSecret(des);
    }

    public static void main(String[] args) {
        String str = "{\"a\":1, \"b\":\"test测试\"}";
        String secretKey = "12345678";
        String s1 = DES.encrypt(str, secretKey);
        System.out.println(s1);
        System.out.println(DES.decrypt(s1, secretKey));
    }
}
