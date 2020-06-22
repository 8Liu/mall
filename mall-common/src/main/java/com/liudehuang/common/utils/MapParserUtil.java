package com.liudehuang.common.utils;

import com.liudehuang.common.exception.ConvertException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: liudh
 * @CreateDate: 2020/6/22 10:25
 * @UpdateUser: liudh
 * @UpdateDate: 2020/6/22 10:25
 * @UpdateRemark:
 * @Version:
 */
public class MapParserUtil {
    protected MapParserUtil() {
    }

    public static Map<String, Object> objectToMap(Object obj) throws Exception {
        Map<String, Object> map = new HashMap();
        if (null == obj) {
            return map;
        } else {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            PropertyDescriptor[] var4 = propertyDescriptors;
            int var5 = propertyDescriptors.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                PropertyDescriptor property = var4[var6];
                String key = property.getName();
                if (!"class".equals(key)) {
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    map.put(key, value);
                }
            }

            return map;
        }
    }

    public static Object getObjectFromMap(Map<String, ? extends Object> map, String key) {
        return StringUtils.isEmpty(key) ? null : map.get(key);
    }

    public static String getStringFromMap(Map<String, ? extends Object> map, String key) {
        return getStringFromMap(map, key, (String)null);
    }

    public static String getStringFromMap(Map<String, ? extends Object> map, String key, String defaultValue) {
        Object result = getObjectFromMap(map, key);
        return null == result ? defaultValue : result.toString();
    }

    public static Integer getIntFromMap(Map<String, ? extends Object> map, String key) {
        Object result = getObjectFromMap(map, key);
        if (null == result) {
            return null;
        } else if (result instanceof Integer) {
            return (Integer)result;
        } else if (result instanceof Long) {
            return ((Long)result).intValue();
        } else {
            String dataString = result.toString();
            return StringUtils.isBlank(dataString) ? null : Integer.parseInt(dataString.trim());
        }
    }

    public static Long getLongFromMap(Map<String, ? extends Object> map, String key) {
        Object result = getObjectFromMap(map, key);
        if (null == result) {
            return null;
        } else if (result instanceof Long) {
            return (Long)result;
        } else if (result instanceof Date) {
            return ((Date)result).getTime();
        } else if (result instanceof Calendar) {
            return ((Calendar)result).getTimeInMillis();
        } else {
            String dataString = result.toString();
            return StringUtils.isBlank(dataString) ? null : Long.parseLong(dataString.trim());
        }
    }

    public static BigDecimal getBigDecimalFromMap(Map<String, ? extends Object> map, String key) {
        Object result = getObjectFromMap(map, key);
        if (null == result) {
            return null;
        } else if (result instanceof BigDecimal) {
            return (BigDecimal)result;
        } else if (result instanceof Double) {
            return new BigDecimal((Double)result);
        } else if (result instanceof Integer) {
            return new BigDecimal((Integer)result);
        } else if (result instanceof Long) {
            return new BigDecimal((Long)result);
        } else {
            String dataString = result.toString();
            return StringUtils.isBlank(dataString) ? null : new BigDecimal(dataString.trim());
        }
    }

    public static Date getDateFromMap(Map<String, ? extends Object> map, String key) throws ConvertException {
        return getDateFromMap(map, key, "yyyy-MM-dd", false);
    }

    public static Date getUTCDateFromMap(Map<String, ? extends Object> map, String key) throws ConvertException {
        return getDateFromMap(map, key, "yyyy-MM-dd'T'HH:mm:ss.SSS Z", true);
    }

    public static Date getDateFromMap(Map<String, ? extends Object> map, String key, String pattern) throws ConvertException {
        return getDateFromMap(map, key, pattern, false);
    }

    public static Date getUTCDateFromMap(Map<String, ? extends Object> map, String key, String pattern) throws ConvertException {
        return getDateFromMap(map, key, pattern, true);
    }

    public static Date getDateFromMap(Map<String, ? extends Object> map, String key, String pattern, boolean utcFlag) throws ConvertException {
        Object result = getObjectFromMap(map, key);
        if (null == result) {
            return null;
        } else if (result instanceof Date) {
            return (Date)result;
        } else if (result instanceof Calendar) {
            return ((Calendar)result).getTime();
        } else if (result instanceof Long) {
            return new Date((Long)result);
        } else if (result instanceof String && !StringUtils.isBlank(result.toString())) {
            try {
                return DateUtil.formatDateTimeStr(result.toString().trim(), pattern, utcFlag);
            } catch (Exception var6) {
                throw new ConvertException(var6);
            }
        } else {
            return null;
        }
    }
}
