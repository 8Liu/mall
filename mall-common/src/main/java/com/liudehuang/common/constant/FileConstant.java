package com.liudehuang.common.constant;

/**
 * @Description:
 * @Author: liudh
 * @CreateDate: 2020/6/22 10:16
 * @UpdateUser: liudh
 * @UpdateDate: 2020/6/22 10:16
 * @UpdateRemark:
 * @Version:
 */
public class FileConstant {
    public static final String REPORT_SUFFIX_REGULAR = "^(XLS)|(xls)|(XLSX)|(xlsx)$";
    public static final String XLS_SUFFIX_REGULAR = "^(XLS)|(xls)$";
    public static final String XLSX_SUFFIX_REGULAR = "^(XLSX)|(xlsx)$";
    public static final String CSV_SUFFIX_REGULAR = "^(CSV)|(csv)$";
    public static final String TXT_SUFFIX_REGULAR = "^(TXT)|(txt)$";
    public static final String XLSX_SUFFIX = "XLSX";
    public static final Integer READ_DEFAULT_SHEET_MAXIMUM = 10000;
    public static final Integer WRITE_SHEET_MAXIMUM = 60000;

    public FileConstant() {
    }
}
