package com.liudehuang.common.utils;

import com.liudehuang.common.constant.CharacterConstant;
import com.liudehuang.common.constant.CommonConstant;
import com.liudehuang.common.constant.FileConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.util.CollectionUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * @Description:
 * @Author: liudh
 * @CreateDate: 2020/6/22 10:30
 * @UpdateUser: liudh
 * @UpdateDate: 2020/6/22 10:30
 * @UpdateRemark:
 * @Version:
 */
@Slf4j
public class ExcelWriteUtil extends ExcelCommonUtil {
    protected ExcelWriteUtil() {
    }

    private static final String STYLE_TITLE = "title";
    private static final String STYLE_STRING = "string";
    private static final String STYLE_NUMBER = "number";
    private static final String STYLE_DATE = "date";

    /**
     * 导出
     *
     * @param fileOpStream 文件输出流
     * @param dataList     数据列表
     * @param headList     EXCEL文件列表头信息列表
     * @param fileSuffix   生成文件的后缀名
     * @throws Exception
     */
    public static void writeExcelFile(OutputStream fileOpStream,
                                      List<List<Object>> dataList,
                                      List<String> headList,
                                      String fileSuffix)
            throws IOException {
        if (matches(FileConstant.CSV_SUFFIX_REGULAR, fileSuffix)
                || matches(FileConstant.TXT_SUFFIX_REGULAR, fileSuffix)) {
            writeCSV(fileOpStream, dataList, headList);
        } else {
            writeExcelWorkbook(fileOpStream, dataList, headList, fileSuffix);
        }
    }

    /**
     * 导出CSV文件
     *
     * @param fileOpStream 文件输出流
     * @param dataList     数据列表
     * @param headList     EXCEL文件列表头信息列表
     * @throws IOException
     */
    public static void writeCSV(OutputStream fileOpStream,
                                List<List<Object>> dataList,
                                List<String> headList)
            throws IOException {
        // 输出流为空
        if (fileOpStream == null) {
            return;
        }
        OutputStreamWriter outputStreamWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            outputStreamWriter = new OutputStreamWriter(fileOpStream, CharacterConstant.GBK);
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            // 写入标题
            if (!CollectionUtils.isEmpty(headList)) {
                for (String title : headList) {
                    bufferedWriter.write(title);
                    bufferedWriter.write(CommonConstant.STRING_COMMA);
                }
                // 换行
                bufferedWriter.write("\r\n");
            }
            // 写内容
            if (!CollectionUtils.isEmpty(dataList)) {
                for (List<Object> rowDataList : dataList) {
                    if (CollectionUtils.isEmpty(rowDataList)) {
                        continue;
                    }
                    for (Object dataValue : rowDataList) {
                        if (null != dataValue) {
                            if (dataValue instanceof Date) {
                                bufferedWriter.write(DateUtil.formatDate((Date) dataValue, DateUtil.EXCEL_DATE_TIME));
                            } else {
                                bufferedWriter.write(dataValue.toString());
                            }
                        }
                        bufferedWriter.write(CommonConstant.STRING_COMMA);
                    }
                    // 写完一行换行
                    bufferedWriter.write("\r\n");
                }
            }
            bufferedWriter.flush();
        } finally {
            StreamUtil.closeWriter(bufferedWriter);
            StreamUtil.closeWriter(outputStreamWriter);
            StreamUtil.closeOutputStream(fileOpStream);
        }
    }

    /**
     * 创建EXCEL工作薄Workbook
     *
     * @param fileOpStream 文件输出流
     * @param dataList     数据列表
     * @param headList     EXCEL文件列表头信息列表
     * @param fileSuffix   生成文件的后缀名
     * @return
     */
    @SuppressWarnings({"all"})
    public static void writeExcelWorkbook(OutputStream fileOpStream,
                                          List<List<Object>> dataList,
                                          List<String> headList,
                                          String fileSuffix)
            throws IOException {

        // 如果保存的对象中没有数据
        if (CollectionUtils.isEmpty(dataList)) {
            return;
        }
        // 输出流为空
        if (null == fileOpStream) {
            return;
        }

        // 根据文件后缀声明一个工作薄
        Workbook workbook = null;
        try {
            if (matches(FileConstant.XLSX_SUFFIX_REGULAR, fileSuffix)) {
                // EXCEL 2007
                workbook = new SXSSFWorkbook();
            } else if (matches(FileConstant.XLS_SUFFIX_REGULAR, fileSuffix)) {
                // EXCEL 2003
                workbook = new HSSFWorkbook();
            } else {
                return;
            }

            // 创建单元格样式
            Map<String, CellStyle> styleMap = new HashMap<String, CellStyle>();
            styleMap.put(STYLE_TITLE, getTitleCellStyle(workbook));
            styleMap.put(STYLE_NUMBER, getNumberCellStyle(workbook));
            styleMap.put(STYLE_STRING, getStrCellStyle(workbook));
            styleMap.put(STYLE_DATE, getDateCellStyle(workbook));

            int sheetTables = dataList.size() / FileConstant.WRITE_SHEET_MAXIMUM;
            if (dataList.size() % FileConstant.WRITE_SHEET_MAXIMUM > 0) {
                sheetTables++;
            }

            for (int sheetIndex = 0; sheetIndex < sheetTables; sheetIndex++) {
                // 生成EXCEL Workbook工作表Sheet
                writeExcelSheet(dataList, headList, workbook, sheetIndex, styleMap);
            }
            workbook.write(fileOpStream);
        } finally {
            closeWorkbook(workbook);
            StreamUtil.closeOutputStream(fileOpStream);
        }
    }

    /**
     * 创建EXCEL工作表Sheet
     *
     * @param dataList   数据列表
     * @param headList   EXCEL文件列表头信息列表
     * @param workbook   EXCEL工作薄
     * @param sheetIndex 创建工作表sheet的下标
     * @param styleMap   单元格样式MAP
     */
    private static void writeExcelSheet(List<List<Object>> dataList,
                                        List<String> headList,
                                        Workbook workbook,
                                        Integer sheetIndex,
                                        Map<String, CellStyle> styleMap) {
        // 生成一个表格
        Sheet sheet = workbook.createSheet("Sheet" + sheetIndex + 1);

        int startRowIndex = 0;
        // 创建标题行
        if (!CollectionUtils.isEmpty(headList)) {
            Row titleRow = sheet.createRow(startRowIndex);
            for (int i = 0; i < headList.size(); i++) {
                Cell cell = titleRow.createCell(i);
                cell.setCellStyle(styleMap.get(STYLE_TITLE));
                cell.setCellValue(headList.get(i));
            }
            startRowIndex++;
        }

        int writeDataSize = (sheetIndex + 1) * FileConstant.WRITE_SHEET_MAXIMUM;
        if (writeDataSize > dataList.size()) {
            writeDataSize = dataList.size();
        }
        // 循环数据创建数据行
        for (int i = sheetIndex * FileConstant.WRITE_SHEET_MAXIMUM; i < writeDataSize; i++) {
            // 获取行数据对象
            List<Object> rowDataList = dataList.get(i);
            // 创建EXCEL数据行
            writeExcelRow(rowDataList, sheet, startRowIndex, styleMap);
            startRowIndex++;
        }
    }

    /**
     * 创建EXCEL数据行
     *
     * @param rowDataList 数据行列表
     * @param sheet       EXCEL工作表
     * @param rowIndex    创建工作表sheet记录行的下标
     * @param styleMap    单元格样式MAP
     */
    private static void writeExcelRow(List<Object> rowDataList,
                                      Sheet sheet,
                                      int rowIndex,
                                      Map<String, CellStyle> styleMap) {
        // 新建一行EXCEL表格行
        Row dataRow = sheet.createRow(rowIndex);
        for (int column = 0; column < rowDataList.size(); column++) {
            // 新建EXCEL表格单元格
            Cell cell = dataRow.createCell(column);
            // 取出行对应单元格数据, 并根据数据格式设置单元格样式存放到Cell
            Object value = rowDataList.get(column);
            if (value == null) {
                // 空值
                cell.setCellStyle(styleMap.get(STYLE_STRING));
            } else if (value instanceof BigInteger) {
                // 值的类型为BigInteger
                cell.setCellStyle(styleMap.get(STYLE_NUMBER));
                cell.setCellValue(((BigInteger) value).doubleValue());
            } else if (value instanceof BigDecimal) {
                // 值的类型为BigDecimal
                cell.setCellStyle(styleMap.get(STYLE_NUMBER));
                cell.setCellValue(((BigDecimal) value).doubleValue());
            } else if (value instanceof Long) {
                // 值的类型为数据类型Long
                cell.setCellStyle(styleMap.get(STYLE_NUMBER));
                cell.setCellValue(((Long) value).doubleValue());
            } else if (value instanceof Integer) {
                // 值的类型为数据类型Integer
                cell.setCellStyle(styleMap.get(STYLE_NUMBER));
                cell.setCellValue(((Integer) value).doubleValue());
            } else if (value instanceof Short) {
                // 值的类型为数据类型Short
                cell.setCellStyle(styleMap.get(STYLE_NUMBER));
                cell.setCellValue(((Short) value).doubleValue());
            } else if (value instanceof Double) {
                // 值的类型为数据类型Double
                cell.setCellStyle(styleMap.get(STYLE_NUMBER));
                cell.setCellValue(((Double) value).doubleValue());
            } else if (value instanceof Float) {
                // 值的类型为数据类型Float
                cell.setCellStyle(styleMap.get(STYLE_NUMBER));
                cell.setCellValue(((Float) value).doubleValue());
            } else if (value instanceof Boolean) {
                // 值的类型为数据类型Boolean
                cell.setCellStyle(styleMap.get(STYLE_STRING));
                cell.setCellValue((Boolean) value);
            } else if (value instanceof Date) {
                // 值的类型为Date日期类型
                cell.setCellStyle(styleMap.get(STYLE_DATE));
                cell.setCellValue((Date) value);
            } else if (value instanceof Calendar) {
                // 值的类型为Calendar日期类型
                cell.setCellStyle(styleMap.get(STYLE_DATE));
                cell.setCellValue((Calendar) value);
            } else {
                // 值的类型为String, Character 或其它
                cell.setCellStyle(styleMap.get(STYLE_STRING));
                cell.setCellValue(value.toString());
            }
        }
    }

    /**
     * 获取 Excel 头单元格样式
     *
     * @param wb
     * @return
     */
    private static CellStyle getTitleCellStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        // 自动换行
        // style.setWrapText(true);

        // 设置边框样式
        setBorderStyle(style, BorderStyle.THIN);

        // 对齐方式
        setCellAlignment(style, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);

        // 设置字体
        Font titleFont = wb.createFont();
        // titleFont.setFontName("华文行楷");// 设置字体名称
        // titleFont.setFontHeightInPoints((short)28);// 设置字号
        titleFont.setItalic(false); // 设置是否为斜体
        titleFont.setBold(true); // 设置是否加粗
        titleFont.setColor(IndexedColors.WHITE.index); // 设置字体颜色
        style.setFont(titleFont);

        // 设置背景
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.index);
        return style;
    }

    /**
     * 获取 Excel 普通字符单元格样式
     *
     * @param wb
     * @return
     */
    private static CellStyle getStrCellStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();

        // 设置边框样式
        setBorderStyle(style, BorderStyle.THIN);

        // 对齐方式
        setCellAlignment(style, HorizontalAlignment.LEFT, VerticalAlignment.CENTER);

        // 字体
        Font font = wb.createFont();
        style.setFont(font);
        return style;
    }

    /**
     * 获取 Excel 数字单元格样式
     *
     * @param wb
     * @return
     */
    private static CellStyle getNumberCellStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();

        // 设置边框样式
        setBorderStyle(style, BorderStyle.THIN);

        // 对齐方式
        setCellAlignment(style, HorizontalAlignment.RIGHT, VerticalAlignment.CENTER);

        // 字体
        Font font = wb.createFont();
        style.setFont(font);
        return style;
    }

    /**
     * 获取 Excel 日期单元格样式
     *
     * @param wb
     * @return
     */
    private static CellStyle getDateCellStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();

        // 设置数据格式
        CreationHelper createHelper = wb.getCreationHelper();
        style.setDataFormat(createHelper.createDataFormat().getFormat(DateUtil.YEAR_MONTH_DAY_HH_MM_SS));

        // 设置边框样式
        setBorderStyle(style, BorderStyle.THIN);

        // 对齐方式
        setCellAlignment(style, HorizontalAlignment.RIGHT, VerticalAlignment.CENTER);

        // 字体
        Font font = wb.createFont();
        style.setFont(font);
        return style;
    }

    /**
     * 设置边框样式
     *
     * @param style
     * @param borderStyle
     */
    private static void setBorderStyle(CellStyle style, BorderStyle borderStyle) {
        // 加边框
        style.setBorderTop(borderStyle); // 上边框
        style.setBorderBottom(borderStyle); // 下边框
        style.setBorderLeft(borderStyle); // 左边框
        style.setBorderRight(borderStyle); // 右边框
    }

    /**
     * 设置单元格内容对齐方式
     *
     * @param style
     * @param horizontalAlignment 水平对齐
     * @param verticalAlignment   垂直对齐
     */
    private static void setCellAlignment(CellStyle style,
                                         HorizontalAlignment horizontalAlignment,
                                         VerticalAlignment verticalAlignment) {
        // 水平对齐
        style.setAlignment(horizontalAlignment);
        // 垂直对齐
        style.setVerticalAlignment(verticalAlignment);
    }
}