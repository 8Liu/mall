package com.liudehuang.common.utils;

import com.liudehuang.common.exception.CodecException;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/**
 * @Description:
 * @Author: liudh
 * @CreateDate: 2020/6/22 10:23
 * @UpdateUser: liudh
 * @UpdateDate: 2020/6/22 10:23
 * @UpdateRemark:
 * @Version:
 */
public class Encodes extends Base64Codec {
    private static final char[] BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    public Encodes() {
    }

    public static String encodeHex(byte[] input) {
        return new String(Hex.encodeHex(input));
    }

    public static byte[] decodeHex(String input) {
        try {
            return Hex.decodeHex(input.toCharArray());
        } catch (DecoderException var2) {
            throw new CodecException(var2);
        }
    }

    public static String encodeBase62(byte[] input) {
        char[] chars = new char[input.length];

        for(int i = 0; i < input.length; ++i) {
            chars[i] = BASE62[(input[i] & 255) % BASE62.length];
        }

        return new String(chars);
    }

    public static String escapeHtml(String html) {
        return StringEscapeUtils.escapeHtml4(html);
    }

    public static String unescapeHtml(String htmlEscaped) {
        return StringEscapeUtils.unescapeHtml4(htmlEscaped);
    }

    public static String escapeXml(String xml) {
        return StringEscapeUtils.escapeXml10(xml);
    }

    public static String unescapeXml(String xmlEscaped) {
        return StringEscapeUtils.unescapeXml(xmlEscaped);
    }

    public static String urlEncode(String part) {
        try {
            return URLEncoder.encode(part, "UTF-8");
        } catch (UnsupportedEncodingException var2) {
            throw new CodecException(var2);
        }
    }

    public static String urlDecode(String part) {
        try {
            return URLDecoder.decode(part, "UTF-8");
        } catch (UnsupportedEncodingException var2) {
            throw new CodecException(var2);
        }
    }

    public static String encodeToMD5(String str) {
        return encodeToAlgorithm(str, "MD5");
    }

    public static String encodeToAlgorithm(String str, String algorithm) {
        if (StringUtils.isEmpty(str)) {
            return null;
        } else {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
                byte[] digest = messageDigest.digest(str.getBytes());
                return new String(Hex.encodeHex(digest));
            } catch (NoSuchAlgorithmException var4) {
                throw new CodecException(var4);
            }
        }
    }

    public static String sortParam(Map<String, ? extends Object> map) {
        ArrayList<String> list = new ArrayList();
        Iterator var2 = map.entrySet().iterator();

        while(var2.hasNext()) {
            Map.Entry<String, ?> entry = (Map.Entry)var2.next();
            if (null != entry.getValue() && !"".equals(entry.getValue())) {
                list.add((String)entry.getKey() + "=" + entry.getValue() + "&");
            }
        }

        if (list.isEmpty()) {
            return null;
        } else {
            int size = list.size();
            String[] arrayToSort = (String[])list.toArray(new String[size]);
            Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
            StringBuilder stringSignTemp = new StringBuilder();

            for(int i = 0; i < size; ++i) {
                stringSignTemp.append(arrayToSort[i]);
            }

            return stringSignTemp.toString();
        }
    }
}