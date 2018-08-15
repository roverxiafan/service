package com.example.util.encrypt;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @title: AES
 * @Description: AES加解密
 * @author: roverxiafan
 * @date: 2016/11/7 15:56
 */
@Slf4j
public class AES {
    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * AES 加密操作
     *
     * @param content 待加密内容
     * @param password 加密密码
     * @return 返回Base64转码后的加密数据
     */
    public static String encrypt(String content, String password) {
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(password));
            byte[] result = cipher.doFinal(byteContent);
            return Base64.encodeBase64String(result);
        } catch (Exception e) {
            log.error("AES encrypt exception", e);
        }

        return null;
    }

    /**
     * AES 解密操作
     *
     * @param content Base64转码后的加密数据
     * @param password 解密密码
     * @return 解密内容
     */
    public static String decrypt(String content, String password) {

        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(password));
            byte[] result = cipher.doFinal(Base64.decodeBase64(content));
            return new String(result, "utf-8");
        } catch (Exception e) {
            log.error("AES decrypt exception", e);
        }

        return null;
    }

    private static SecretKeySpec getSecretKey(final String password) throws NoSuchAlgorithmException {
            KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
            kg.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kg.generateKey();
            return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);
    }

    public static void main(String[] args) {
        String str = "{\"a\":1, \"b\":\"test测试\"}";
        String secretKey = "a&e1i*sdf5u*1";
        String s1 = AES.encrypt(str, secretKey);
        System.out.println(s1);
        System.out.println(AES.decrypt(s1, secretKey));
    }
}
