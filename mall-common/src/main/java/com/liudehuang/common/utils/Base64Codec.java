package com.liudehuang.common.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;

/**
 * @Description:
 * @Author: liudh
 * @CreateDate: 2020/6/22 10:20
 * @UpdateUser: liudh
 * @UpdateDate: 2020/6/22 10:20
 * @UpdateRemark:
 * @Version:
 */
public class Base64Codec {

    protected Base64Codec() {
    }

    public static String encode(byte[] binaryData) throws UnsupportedEncodingException {
        return null != binaryData && binaryData.length > 0 ? new String(Base64.encodeBase64(binaryData), "UTF-8") : null;
    }

    public static String encryptBASE64(String data) throws UnsupportedEncodingException {
        return !StringUtils.isEmpty(data) ? encode(data.getBytes()) : null;
    }

    public static byte[] decode(String base64String) throws UnsupportedEncodingException {
        return !StringUtils.isEmpty(base64String) ? Base64.decodeBase64(base64String.getBytes("UTF-8")) : new byte[0];
    }

    public static String decryptBASE64(String base64String) throws UnsupportedEncodingException {
        byte[] byteArray = decode(base64String);
        return null != byteArray && byteArray.length > 0 ? new String(byteArray) : null;
    }
}
