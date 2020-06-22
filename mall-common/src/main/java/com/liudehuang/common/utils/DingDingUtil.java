package com.liudehuang.common.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 发送钉钉通知工具类
 */
@Slf4j
public class DingDingUtil {

    /**
     * 钉钉通知默认数据格式
     */
    private static final String CONTENT_TYPE = "application/json;charset=utf-8";

    /**
     * @param robotUrl Webhook URL（配置机器人是提供）
     * @param secret   Webhook密钥
     * @param content  发送内容
     * @return
     * @throws Exception
     */
    public static String send(String robotUrl, String secret, String content) {
        if (StringUtils.isEmpty(content)) {
            log.warn("发送内容为空，默认不发送.");
            return "";
        }
        try {
            String result = HttpRequestUtil.sendPost(robotUrl + "&" + buildSignParam(secret), buildReqStr(content), CONTENT_TYPE);
            return result;
        } catch (Exception e) {
            log.error("发送钉钉消息失败(单条消息)", e);
        }
        return null;
    }

    /**
     * @param robotUrl Webhook URL（配置机器人是提供）
     * @param secret   Webhook密钥
     * @param contents 发送内容集合
     * @return
     * @throws Exception
     */
    public static String send(String robotUrl, String secret, List<String> contents) {
        if (CollectionUtils.isEmpty(contents)) {
            log.warn("发送内容为空，默认不发送.");
            return "";
        }
        try {
            String result = HttpRequestUtil.sendPost(robotUrl + "&" + buildSignParam(secret), buildReqStr(contents), CONTENT_TYPE);
            return result;
        } catch (Exception e) {
            log.error("发送钉钉消息失败(多条消息)", e);
        }
        return null;
    }

    /**
     * @param secret 配置robot时提供的密钥
     * @return
     */
    private static String buildSignParam(String secret) throws Exception {
        Long timestamp = System.currentTimeMillis();
        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
        return "timestamp=" + timestamp + "&sign=" + sign;
    }

    private static String buildReqStr(String content) {
        List<String> list = new ArrayList<String>();
        list.add(content);
        return buildReqStr(list);
    }

    private static String buildReqStr(List<String> contentList) {
        String content = "";
        for (String str : contentList) {
            content += str;
        }
        //消息内容
        Map<String, String> contentMap = new HashMap();
        contentMap.put("content", content);
        //通知人
        Map<String, Object> atMap = new HashMap();
        //通知所有人
        atMap.put("isAtAll", true);
        Map<String, Object> reqMap = new HashMap();
        reqMap.put("msgtype", "text");
        reqMap.put("text", contentMap);
        reqMap.put("at", atMap);
        return JSONObject.toJSONString(reqMap);
    }

}