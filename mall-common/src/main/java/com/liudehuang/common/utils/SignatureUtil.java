package com.liudehuang.common.utils;

import com.alibaba.fastjson.JSON;
import com.liudehuang.common.constant.AlgorithmConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * @Description:
 * @Author: liudh
 * @CreateDate: 2020/6/22 10:15
 * @UpdateUser: liudh
 * @UpdateDate: 2020/6/22 10:15
 * @UpdateRemark:
 * @Version:
 */
@Slf4j
public class SignatureUtil {

    protected SignatureUtil() {
    }

    private static final String SIGN = "sign";

    /**
     * MD5签名算法
     *
     * @param o      要参与签名的数据对象
     * @param apiKey API密钥
     * @return 签名
     */
    public static String getSign(Object o, String apiKey) {
        return SignatureUtil.getSign(o, apiKey, AlgorithmConstant.MD5);
    }

    /**
     * 签名算法
     *
     * @param dataInfo  要参与签名的数据object
     * @param apiKey    API密钥
     * @param algorithm 运算法测
     * @return 签名
     */
    public static String getSign(Object dataInfo, String apiKey, String algorithm) {
        if (null == dataInfo) {
            return null;
        } else if (dataInfo instanceof Map) {
            return getSignFromMap((Map) dataInfo, apiKey, algorithm);
        } else {
            return getSignFromObject(dataInfo, apiKey, algorithm);
        }
    }

    /**
     * MD5签名算法
     *
     * @param dataInfo 要参与签名的数据object
     * @param apiKey   API密钥
     * @return 签名
     */
    public static String getSignFromObject(Object dataInfo, String apiKey) {
        return getSignFromObject(dataInfo, apiKey, AlgorithmConstant.MD5);
    }

    /**
     * 签名算法
     *
     * @param dataInfo  要参与签名的数据object
     * @param apiKey    API密钥
     * @param algorithm 运算法测
     * @return 签名
     */
    public static String getSignFromObject(Object dataInfo, String apiKey, String algorithm) {
        if (null == dataInfo) {
            return null;
        }

        ArrayList<String> list = new ArrayList<String>();
        Class cls = dataInfo.getClass();
        // 获取类所有方法
        Method[] methodArr = cls.getMethods();
        for (Method method : methodArr) {
            String methodName = method.getName();
            Object propertyValue = null;
            try {
                if (methodName.startsWith("get") && !"getClass".equals(methodName)) {
                    // 获取属性值
                    propertyValue = method.invoke(dataInfo);
                } else {
                    continue;
                }
            } catch (Exception e) {
                log.error("error:" + method, e);
            }

            if (null != propertyValue && !"".equals(propertyValue)) {
                boolean pushDataFlag = true;
                if (propertyValue instanceof Collection) {
                    if (CollectionUtils.isEmpty((Collection) propertyValue)) {
                        pushDataFlag = false;
                    } else {
                        propertyValue = JSON.toJSONString(propertyValue);
                    }
                } else if (propertyValue instanceof Map) {
                    if (CollectionUtils.isEmpty((Map) propertyValue)) {
                        pushDataFlag = false;
                    } else {
                        propertyValue = JSON.toJSONString(propertyValue);
                    }
                }
                if (pushDataFlag) {
                    String key = methodName.substring(3);
                    key = key.substring(0, 1).toLowerCase() + key.substring(1);
                    list.add(key + "=" + propertyValue + "&");
                }
            }
        }
        return createSign(list, apiKey, algorithm);
    }

    /**
     * MD5签名算法
     *
     * @param map    要参与签名的数据Map
     * @param apiKey API密钥
     * @return 签名
     */
    public static String getSignFromMap(Map<String, ? extends Object> map, String apiKey) {
        return SignatureUtil.getSignFromMap(map, apiKey, AlgorithmConstant.MD5);
    }

    /**
     * 签名算法
     *
     * @param map       要参与签名的数据Map
     * @param apiKey    API密钥
     * @param algorithm 运算法测
     * @return 签名
     */
    public static String getSignFromMap(Map<String, ? extends Object> map, String apiKey, String algorithm) {
        if (CollectionUtils.isEmpty(map)) {
            return null;
        }

        ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            if (null == entry.getValue() || "".equals(entry.getValue())
                    || SIGN.equalsIgnoreCase(entry.getKey())) {
                continue;
            }

            if (entry.getValue() instanceof Collection
                    && !CollectionUtils.isEmpty((Collection) entry.getValue())) {
                list.add(entry.getKey() + "=" + JSON.toJSONString(entry.getValue()) + "&");
            } else if (entry.getValue() instanceof Map
                    && !CollectionUtils.isEmpty((Map) entry.getValue())) {
                list.add(entry.getKey() + "=" + JSON.toJSONString(entry.getValue()) + "&");
            } else {
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        return createSign(list, apiKey, algorithm);
    }

    /**
     * 生成签名
     *
     * @param list      (key=value&)类型字符串列表
     * @param apiKey    签名密钥
     * @param algorithm 运算法测
     * @return 签名
     */
    private static String createSign(ArrayList<String> list, String apiKey, String algorithm) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort);
        StringBuilder stringSignTemp = new StringBuilder();
        for (int i = 0; i < size; i++) {
            stringSignTemp.append(arrayToSort[i]);
        }
        String signTempStr;
        if (!StringUtils.isEmpty(apiKey)) {
            stringSignTemp.append("key=" + apiKey);
            signTempStr = stringSignTemp.toString();
        } else {
            signTempStr = stringSignTemp.substring(0, stringSignTemp.length() - 1);
        }
        log.debug("createSign Before MD5: {}", signTempStr);
        String result = Encodes.encodeToAlgorithm(signTempStr, algorithm).toUpperCase();
        log.debug("createSign Result: {}", result);
        return result;
    }

    /**
     * 检验API返回的数据里面的签名是否合法，避免数据在传输的过程中被第三方篡改
     *
     * @param map    API返回的MAP数据
     * @param apiKey API密钥
     * @return API签名是否合法
     */
    public static boolean checkIsSignValidFromData(Map<String, ? extends Object> map, String apiKey) {

        String signFromAPIResponse = map.get(SIGN).toString();
        if (StringUtils.isEmpty(signFromAPIResponse)) {
            log.info("API返回的数据签名数据不存在，有可能被第三方篡改!");
            return false;
        }
        // 清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
        map.put(SIGN, null);
        // 将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
        String signForAPIResponse = SignatureUtil.getSignFromMap(map, apiKey, AlgorithmConstant.MD5);

        if (StringUtils.isEmpty(signForAPIResponse) || !signFromAPIResponse.equals(signForAPIResponse)) {
            // 签名验不过，表示这个API返回的数据有可能已经被篡改了
            log.info("API返回的数据签名验证不通过，有可能被第三方篡改!");
            return false;
        }
        return true;
    }

    /**
     * 校验MD5签名
     *
     * @param object  要参与签名的数据object
     * @param oldSign 原签名
     * @param key     密钥
     * @return
     */
    public static boolean signVerify(Object object, String oldSign, String key) {
        return signVerify(object, oldSign, key, AlgorithmConstant.MD5);
    }

    /**
     * 校验签名
     *
     * @param object    要参与签名的数据object
     * @param oldSign   原签名
     * @param key       密钥
     * @param algorithm 运算法测
     * @return
     */
    public static boolean signVerify(Object object, String oldSign, String key, String algorithm) {
        if (StringUtils.isEmpty(oldSign)) {
            log.info("API返回的数据签名数据不存在，有可能被第三方篡改!!!");
            return false;
        } else {
            String sign = getSign(object, key, algorithm);
            return oldSign.equals(sign);
        }
    }
}