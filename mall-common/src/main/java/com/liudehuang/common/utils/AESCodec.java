package com.liudehuang.common.utils;

import com.liudehuang.common.constant.AESConstant;
import com.liudehuang.common.exception.CodecException;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * @Description:
 * @Author: liudh
 * @CreateDate: 2020/6/22 10:18
 * @UpdateUser: liudh
 * @UpdateDate: 2020/6/22 10:18
 * @UpdateRemark:
 * @Version:
 */
public class AESCodec {
    protected AESCodec() {
    }

    public static String genAESKey() throws Exception {
        return genAESKey(AESConstant.KEY_SIZE_128);
    }

    public static String genAESKey(Integer keySize) throws Exception {
        return getAESKey(keySize, (String)null);
    }

    public static String getAESKey(Integer keySize, String secretKey) throws Exception {
        if (!AESConstant.KEY_SIZE_192.equals(keySize) && !AESConstant.KEY_SIZE_256.equals(keySize)) {
            keySize = AESConstant.KEY_SIZE_128;
        }

        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        if (StringUtils.isNotEmpty(secretKey)) {
            keyGen.init(keySize, new SecureRandom(secretKey.getBytes()));
        } else {
            keyGen.init(keySize);
        }

        SecretKey key = keyGen.generateKey();
        String base64Str = Base64Codec.encode(key.getEncoded());
        return base64Str;
    }

    public static String aesEncrypt(String str, String key) {
        return aesEncrypt(str, key, (String)null, "AES/ECB/PKCS5Padding");
    }

    public static String aesEncrypt(String str, String key, String keyOffset, String transform) {
        try {
            byte[] bytes = encrypt(str, key, keyOffset, transform);
            return Base64Codec.encode(bytes);
        } catch (Exception var5) {
            throw new CodecException(var5.getMessage());
        }
    }

    public static String aesEncryptWith16(String str, String key) {
        return aesEncryptWith16(str, key, (String)null, "AES/ECB/PKCS5Padding");
    }

    public static String aesEncryptWith16(String str, String key, String keyOffset, String transform) {
        try {
            byte[] bytes = encrypt(str, key, keyOffset, transform);
            return Encodes  .encodeHex(bytes);
        } catch (Exception var5) {
            throw new CodecException(var5.getMessage());
        }
    }

    public static byte[] encrypt(String str, String key, String keyOffset, String transform) {
        try {
            if (!StringUtils.isEmpty(str) && !StringUtils.isEmpty(key)) {
                Cipher cipher = createCipher(key, keyOffset, transform, 1);
                return cipher.doFinal(str.getBytes("UTF-8"));
            } else {
                return null;
            }
        } catch (Exception var5) {
            throw new CodecException(var5.getMessage());
        }
    }

    public static String aesDecrypt(String str, String key) {
        return aesDecrypt(str, key, (String)null, "AES/ECB/PKCS5Padding");
    }

    public static String aesDecrypt(String str, String key, String keyOffset, String transform) {
        try {
            if (!StringUtils.isEmpty(str) && !StringUtils.isEmpty(key)) {
                Cipher cipher = createCipher(key, keyOffset, transform, 2);
                byte[] bytes = Base64Codec.decode(str);
                bytes = cipher.doFinal(bytes);
                return new String(bytes, "UTF-8");
            } else {
                return null;
            }
        } catch (Exception var6) {
            throw new CodecException(var6.getMessage());
        }
    }

    public static String aesDecryptWith16(String str, String key) {
        return aesDecryptWith16(str, key, (String)null, "AES/ECB/PKCS5Padding");
    }

    public static String aesDecryptWith16(String str, String key, String keyOffset, String transform) {
        try {
            if (!StringUtils.isEmpty(str) && !StringUtils.isEmpty(key)) {
                Cipher cipher = createCipher(key, keyOffset, transform, 2);
                byte[] bytes = Encodes.decodeHex(str);
                bytes = cipher.doFinal(bytes);
                return new String(bytes, "UTF-8");
            } else {
                return null;
            }
        } catch (Exception var6) {
            throw new CodecException(var6.getMessage());
        }
    }

    private static Cipher createCipher(String key, String keyOffset, String transform, int mode) {
        if (StringUtils.isEmpty(transform)) {
            transform = "AES/ECB/PKCS5Padding";
        }

        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            Cipher cipher;
            if ("AES/CBC/PKCS5Padding".equals(transform)) {
                if (StringUtils.isEmpty(keyOffset)) {
                    throw new CodecException("密钥偏移量不能为空");
                }

                if (!AESConstant.KEY_OFFSET.equals(keyOffset.length())) {
                    throw new CodecException("密钥偏移量只能16字节长度");
                }

                cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                IvParameterSpec ivParameterSpec = new IvParameterSpec(keyOffset.getBytes());
                cipher.init(mode, secretKeySpec, ivParameterSpec);
            } else {
                cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(mode, secretKeySpec);
            }

            return cipher;
        } catch (Exception var7) {
            throw new CodecException(var7.getMessage());
        }
    }
}