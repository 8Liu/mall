package com.liudehuang.common.utils;

import com.liudehuang.common.constant.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:
 * @Author: liudh
 * @CreateDate: 2020/6/22 10:01
 * @UpdateUser: liudh
 * @UpdateDate: 2020/6/22 10:01
 * @UpdateRemark:
 * @Version:
 */
@Slf4j
public abstract class ExcelCommonUtil {
    protected ExcelCommonUtil() {
    }

    /**
     * 正则校验文件名
     *
     * @param regex
     * @param input
     * @return
     */
    public static boolean matches(String regex, String input) {
        if (StringUtils.isEmpty(input)) {
            return false;
        }
        Pattern regexPattern = Pattern.compile(regex);
        Matcher m = regexPattern.matcher(input);
        return m.matches();
    }

    /**
     * 关闭工作薄Workbook
     *
     * @param workbook
     */
    public static void closeWorkbook(Workbook workbook) {
        try {
            if (null != workbook) {
                workbook.close();
            }
        } catch (IOException e) {
            log.error("关闭工作薄操作异常", e);
        }
    }

    /**
     * 获取开始下标
     *
     * @param startIndex
     * @param defaultIndex
     * @return
     */
    public static Integer getStartIndex(Integer startIndex, Integer defaultIndex) {
        if (null == startIndex || startIndex < CommonConstant.INTEGER_0) {
            return defaultIndex;
        }
        return startIndex;
    }
}
