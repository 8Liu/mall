package com.liudehuang.common.utils;

import com.liudehuang.common.exception.CodecException;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: liudh
 * @CreateDate: 2020/6/22 10:33
 * @UpdateUser: liudh
 * @UpdateDate: 2020/6/22 10:33
 * @UpdateRemark:
 * @Version:
 */
public class RSACodec {
    public static final String KEY_ALGORITHM = "RSA";
    private static final int KEY_SIZE = 65536;
    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    private RSACodec() {
    }

    public static Map<String, Object> initKey() throws CodecException {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(65536);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
            Map<String, Object> keyMap = new HashMap();
            keyMap.put("RSAPublicKey", publicKey);
            keyMap.put("RSAPrivateKey", privateKey);
            return keyMap;
        } catch (Exception var5) {
            throw new CodecException(var5.getMessage());
        }
    }

    public static byte[] getPrivateKey(Map<String, Object> keyMap) {
        Key key = (Key)keyMap.get("RSAPrivateKey");
        return key.getEncoded();
    }

    public static byte[] getPublicKey(Map<String, Object> keyMap) {
        Key key = (Key)keyMap.get("RSAPublicKey");
        return key.getEncoded();
    }

    public static byte[] encryptByPublicKey(byte[] data, byte[] key) throws CodecException {
        return codecByPublicKey(data, key, 1);
    }

    public static byte[] decryptByPublicKey(byte[] data, byte[] key) throws CodecException {
        return codecByPublicKey(data, key, 2);
    }

    public static byte[] encryptByPrivateKey(byte[] data, byte[] key) throws CodecException {
        return codecByPrivateKey(data, key, 1);
    }

    public static byte[] decryptByPrivateKey(byte[] data, byte[] key) throws CodecException {
        return codecByPrivateKey(data, key, 2);
    }

    private static byte[] codecByPublicKey(byte[] data, byte[] key, int codecMode) throws CodecException {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
            PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(codecMode, pubKey);
            return cipher.doFinal(data);
        } catch (Exception var7) {
            throw new CodecException(var7.getMessage());
        }
    }

    private static byte[] codecByPrivateKey(byte[] data, byte[] key, int codecMode) throws CodecException {
        try {
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(2, privateKey);
            return cipher.doFinal(data);
        } catch (Exception var7) {
            throw new CodecException(var7.getMessage());
        }
    }
}