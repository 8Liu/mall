package com.liudehuang.common.utils;

import com.liudehuang.common.constant.CharacterConstant;
import com.liudehuang.common.constant.CommonConstant;
import com.liudehuang.common.constant.FileConstant;
import com.liudehuang.common.exception.ConvertException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.util.StringUtils;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: liudh
 * @CreateDate: 2020/6/22 10:03
 * @UpdateUser: liudh
 * @UpdateDate: 2020/6/22 10:03
 * @UpdateRemark:
 * @Version:
 */
@Slf4j
public class ExcelReadUtil extends ExcelCommonUtil {
    protected ExcelReadUtil() {
    }

    /**
     * 解析CSV文件
     *
     * @param fileInputStream
     * @param cls
     * @param propertyList
     * @param readStartRow
     * @param readStartColumn
     * @param <T>
     * @return
     */
    public static <T> List<T> parseCSV(InputStream fileInputStream,
                                       Class<T> cls,
                                       List<String> propertyList,
                                       Integer readStartRow,
                                       Integer readStartColumn) {
        // 初始化一个解析后的结果集对象
        List<T> resultDataList = new ArrayList<T>();
        if (null == fileInputStream) {
            return resultDataList;
        }

        // 默认第一行为模版说明与标题不是数据部分，从row＝1开始读取数据
        readStartRow = getStartIndex(readStartRow, CommonConstant.INTEGER_1);
        readStartColumn = getStartIndex(readStartColumn, CommonConstant.INTEGER_0);
        BufferedReader bufferedReader = null;
        InputStreamReader streamReader = null;
        try {
            // 转换读数据流对象
            streamReader = new InputStreamReader(fileInputStream, CharacterConstant.GBK);
            bufferedReader = new BufferedReader(streamReader);

            // 定义对象
            PropertyAccessor propertyAccessor;
            String propertyField; // 对象属性名
            String rowDataStr; // 行数据字符串
            int currentRowIndex = 0;
            int currentColumnIndex = 0; // 当前处理列号
            // 总数据列
            int countColumn = propertyList.size() + readStartColumn;
            while ((rowDataStr = bufferedReader.readLine()) != null) {
                if (currentRowIndex < readStartRow) {
                    currentRowIndex++;
                    // 去除标题行
                    continue;
                }

                // 截取行数据数组
                String[] rowDataArr = rowDataStr.split(CommonConstant.STRING_COMMA);

                // 创建数据对象
                T dataObj = cls.newInstance();
                propertyAccessor = PropertyAccessorFactory.forDirectFieldAccess(dataObj);

                // 循环解析工作薄Workbook对象行上的列
                currentColumnIndex = readStartColumn;
                for (; currentColumnIndex < countColumn; currentColumnIndex++) {
                    propertyField = propertyList.get(currentColumnIndex);
                    if (StringUtils.isEmpty(rowDataArr[currentColumnIndex])
                            || !propertyAccessor.isWritableProperty(propertyField)) {
                        // 如果单元格为空或数据对象没有对应的属性, 则退出
                        continue;
                    }
                    // 得到字段返回值参数类型
                    Class<?> propertyType = propertyAccessor.getPropertyType(propertyField);
                    propertyAccessor.setPropertyValue(propertyField,
                            getPropertyValueFromString(rowDataArr[currentColumnIndex], propertyType));
                }
                resultDataList.add(dataObj);
            }
        } catch (UnsupportedEncodingException e) {
            throw new ConvertException("CSV文件导入; CSV文件编码异常" + e);
        } catch (IOException e) {
            throw new ConvertException("CSV文件导入; CSV文件读取异常" + e);
        } catch (InstantiationException e) {
            throw new ConvertException("CSV文件导入; CSV文件解析, 实例化返回数据对象异常" + e);
        } catch (IllegalAccessException e) {
            throw new ConvertException("CSV文件导入; CSV文件解析的返回数据对象错误异常" + e);
        } finally {
            // 关闭文件读
            StreamUtil.closeReader(bufferedReader);
            StreamUtil.closeReader(streamReader);
        }
        // 将数据保存到数据库中
        return resultDataList;
    }

    /**
     * 多工作表的EXCEL文件导入(默认最多可读一万条记录)
     *
     * @param fileInputStream 文件输入流
     * @param cls             Class
     * @param propertyList    ArrayList<String>对象属性名列表集合
     * @param readStartRow    数据开始读取行下标(以0开始)
     * @param readStartColumn 数据开始读取列下标(以0开始)
     * @param <T>
     * @return List
     */
    public static <T> List<T> parseExcel(InputStream fileInputStream,
                                         Class<T> cls,
                                         List<String> propertyList,
                                         Integer readStartRow,
                                         Integer readStartColumn) {
        return parseExcel(fileInputStream, cls, propertyList, readStartRow,
                readStartColumn, FileConstant.READ_DEFAULT_SHEET_MAXIMUM);
    }

    /**
     * 多工作表的EXCEL文件导入
     *
     * @param fileInputStream 文件输入流
     * @param cls             Class
     * @param propertyList    ArrayList<String>对象属性名列表集合
     * @param readStartRow    数据开始读取行下标(以0开始)
     * @param readStartColumn 数据开始读取列下标(以0开始)
     * @param readMaxRow      读取数据最大记录行数
     * @param <T>
     * @return List
     */
    public static <T> List<T> parseExcel(InputStream fileInputStream,
                                         Class<T> cls,
                                         List<String> propertyList,
                                         Integer readStartRow,
                                         Integer readStartColumn,
                                         Integer readMaxRow) {
        // 初始化一个解析后的结果集对象
        List<T> results = new ArrayList<T>();
        if (null == fileInputStream) {
            return results;
        }

        int sheetIndex = 0;
        Workbook workbook = null;
        try {
            // 获取工作薄Workbook对象信息
            workbook = WorkbookFactory.create(fileInputStream);
            if (null == workbook) {
                return results;
            }

            // 默认第一行为模版说明与标题不是数据部分，从row＝1开始读取数据
            readStartRow = getStartIndex(readStartRow, CommonConstant.INTEGER_1);
            readStartColumn = getStartIndex(readStartColumn, CommonConstant.INTEGER_0);

            // 循环解析工作薄Workbook对象信息
            for (; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
                Sheet sheet = workbook.getSheetAt(sheetIndex);
                // 解析Excel工作表数据
                parseSheetData(sheet, results, cls, propertyList, readStartRow, readStartColumn, readMaxRow);
            }
        } catch (ConvertException e) {
            throw new ConvertException("excel文件导入; excel文件的[第" + (sheetIndex + 1) + "工作表" + e);
        } catch (Exception ex) {
            throw new ConvertException("excel文件导入; excel文件的[第" + (sheetIndex + 1) + "工作表" + ex);
        } finally {
            // 关闭文件工作薄Workbook
            closeWorkbook(workbook);
            // 关闭输入流
            StreamUtil.closeInputStream(fileInputStream);
        }
        // 将数据保存到数据库中
        return results;
    }

    /**
     * 指定工作表的EXCEL文件导入(默认最多可读一万条记录)
     *
     * @param fileInputStream 文件输入流
     * @param cls             Class
     * @param propertyList    ArrayList<String>对象属性名列表集合
     * @param readStartRow    数据开始读取行下标(以0开始)
     * @param readStartColumn 数据开始读取列下标(以0开始)
     * @param readSheetIndex  工作表下标(以0开始)
     * @param <T>
     * @return List
     */
    public static <T> List<T> parseExcelSheet(InputStream fileInputStream,
                                              Class<T> cls,
                                              List<String> propertyList,
                                              Integer readStartRow,
                                              Integer readStartColumn,
                                              Integer readSheetIndex) {
        return parseExcelSheet(fileInputStream, cls, propertyList, readStartRow,
                readStartColumn, readSheetIndex, FileConstant.READ_DEFAULT_SHEET_MAXIMUM);
    }

    /**
     * 指定工作表的EXCEL文件导入
     *
     * @param fileInputStream 文件输入流
     * @param cls             Class
     * @param propertyList    ArrayList<String>对象属性名列表集合
     * @param readStartRow    数据开始读取行下标(以0开始)
     * @param readStartColumn 数据开始读取列下标(以0开始)
     * @param readSheetIndex  工作表下标(以0开始)
     * @param readMaxRow      读取数据最大记录行数
     * @param <T>
     * @return List
     */
    public static <T> List<T> parseExcelSheet(InputStream fileInputStream,
                                              Class<T> cls,
                                              List<String> propertyList,
                                              Integer readStartRow,
                                              Integer readStartColumn,
                                              Integer readSheetIndex,
                                              Integer readMaxRow) {
        // 初始化一个解析后的结果集对象
        List<T> results = new ArrayList<T>();
        if (null == fileInputStream || null == readSheetIndex || readSheetIndex < 0) {
            return results;
        }

        // 默认第一行为模版说明与标题不是数据部分，从row＝1开始读取数据
        readStartRow = getStartIndex(readStartRow, CommonConstant.INTEGER_1);
        readStartColumn = getStartIndex(readStartColumn, CommonConstant.INTEGER_0);

        Workbook workbook = null;
        try {
            // 获取工作薄Workbook对象信息
            workbook = WorkbookFactory.create(fileInputStream);

            // 解析Excel工作表数据
            parseSheetData(workbook.getSheetAt(readSheetIndex), results, cls,
                    propertyList, readStartRow, readStartColumn, readMaxRow);
        } catch (ConvertException e) {
            throw new ConvertException("excel文件导入; excel文件的[第" + (readSheetIndex + 1) + "工作表" + e);
        } catch (Exception ex) {
            throw new ConvertException("excel文件导入; excel文件的[第" + (readSheetIndex + 1) + "工作表" + ex);
        } finally {
            // 关闭文件工作薄Workbook
            closeWorkbook(workbook);
            // 关闭输入流
            StreamUtil.closeInputStream(fileInputStream);
        }
        // 将数据保存到数据库中
        return results;
    }

    /**
     * 解析Excel工作表数据
     *
     * @param sheet           Excel工作表
     * @param resultDataList  返回数据列表
     * @param cls             Class
     * @param propertyList    ArrayList<String>对象属性名列表集合
     * @param readStartRow    数据开始读取行下标(以0开始)
     * @param readStartColumn 数据开始读取列下标(以0开始)
     * @param readMaxRow      读取数据最大行数
     * @param <T>
     * @return
     */
    private static <T> void parseSheetData(Sheet sheet,
                                           List<T> resultDataList,
                                           Class<T> cls,
                                           List<String> propertyList,
                                           Integer readStartRow,
                                           Integer readStartColumn,
                                           Integer readMaxRow) {
        if (null == sheet) {
            return;
        }
        Integer sheetTotalRows = sheet.getPhysicalNumberOfRows();
        if (sheetTotalRows <= readStartRow) {
            return;
        }
        if (sheetTotalRows > readMaxRow + readStartRow) {
            throw new ConvertException("文件超过" + readMaxRow + "行, 请分批导入.");
        }

        int currentRowIndex = readStartRow; // 当前处理行号
        int currentColumnIndex = 0; // 当前处理列号
        PropertyAccessor propertyAccessor;
        String propertyName; // 对象属性名
        try {
            // 总数据列
            int countColumn = propertyList.size() + readStartColumn;
            // 循环解析工作薄Workbook对象行数据
            for (; currentRowIndex < sheetTotalRows; currentRowIndex++) {
                Row sheetRow = sheet.getRow(currentRowIndex);
                if (null == sheetRow) {
                    break;
                }

                // 创建数据对象
                T dataObj = cls.newInstance();
                propertyAccessor = PropertyAccessorFactory.forDirectFieldAccess(dataObj);
                boolean rowDataIsNull = true;
                // 循环解析工作薄Workbook对象行上的列
                currentColumnIndex = readStartColumn;
                for (; currentColumnIndex < countColumn; currentColumnIndex++) {
                    // 取出Excel表格中的具体一个单元格的数据
                    Cell cell = sheetRow.getCell(currentColumnIndex);
                    // 取出当前列对应的对象属性名
                    propertyName = propertyList.get(currentColumnIndex);
                    if (null == cell || !propertyAccessor.isWritableProperty(propertyName)) {
                        // 如果单元格为空或数据对象没有对应的属性, 则退出
                        continue;
                    }
                    rowDataIsNull = false;
                    // 得到字段返回值参数类型
                    Class<?> propertyType = propertyAccessor.getPropertyType(propertyName);
                    // 解析单元格的数据值
                    Object cellData = parseCellValue(cell, propertyType);
                    // 为对象方法赋值
                    propertyAccessor.setPropertyValue(propertyName, cellData);
                }
                // 如果是行上值都为空，跳出循环行
                if (rowDataIsNull) {
                    break;
                } else {
                    resultDataList.add(dataObj);
                }
            }
        } catch (Exception ex) {
            throw new ConvertException("解析第 " + (currentRowIndex + 1)
                    + "行,第" + (currentColumnIndex + 1) + "列 ]过程中出现异常: " + ex);
        }
    }

    /**
     * 解析单元格的数据值
     *
     * @param cell         单元格
     * @param propertyType 数据对象属性的数据类型
     */
    private static Object parseCellValue(Cell cell, Class<?> propertyType) {
        Object cellData;
        if (CellType.STRING == cell.getCellType()) {
            // 得到当前单元格的（字符类型）的值
            String labelValue = cell.getStringCellValue();
            // 根据属性类型转换字符串类型数据
            cellData = getPropertyValueFromString(labelValue, propertyType);
        } else if (CellType.NUMERIC == cell.getCellType()) {
            // 得到当前单元格的（数值类型）的值
            Double numberValue = cell.getNumericCellValue();

            if (BigInteger.class.equals(propertyType)) {
                // 返回值参数类型BigInteger
                cellData = new BigInteger(Long.toString(numberValue.longValue()));
            } else if (BigDecimal.class.equals(propertyType)) {
                // 返回值参数类型BigDecimal
                cellData = new BigDecimal(numberValue.toString());
            } else if (Long.class.equals(propertyType)) {
                // 返回值参数类型Long
                cellData = numberValue.longValue();
            } else if (Double.class.equals(propertyType)) {
                // 返回值参数类型Double
                cellData = numberValue;
            } else if (Integer.class.equals(propertyType)) {
                // 返回值参数类型Integer
                cellData = numberValue.intValue();
            } else if (Float.class.equals(propertyType)) {
                // 返回值参数类型Float
                cellData = numberValue.floatValue();
            } else if (Date.class.equals(propertyType)) {
                // 日期类型Date
                cellData = cell.getDateCellValue();
            } else if (Boolean.class.equals(propertyType)) {
                // 布尔类型Boolean
                cellData = cell.getBooleanCellValue();
            } else {
                // 返回值参数类型String
                cellData = new BigDecimal(numberValue).toString();
                if (cellData != null) {
                    int index = ((String) cellData).indexOf('.');
                    if (index > 1) {
                        cellData = ((String) cellData).substring(0, index);
                    }
                }
            }
        } else if (CellType.FORMULA == cell.getCellType()) {
            // 得到当前单元格的（日期类型）的值
            if (Date.class.equals(propertyType)) {
                cellData = cell.getDateCellValue();
            } else {
                cellData = cell.getCellFormula();
            }
        } else if (CellType.BOOLEAN == cell.getCellType()) {
            // 得到当前单元格的（布尔类型）的值
            if (Boolean.class.equals(propertyType)) {
                cellData = cell.getBooleanCellValue();
            } else {
                cellData = null;
            }
        } else {
            // 数据类型为空
            cellData = null;
        }
        return cellData;
    }

    /**
     * 根据属性类型转换字符串类型数据
     *
     * @param dataValue
     * @param propertyType
     * @return
     */
    private static Object getPropertyValueFromString(String dataValue, Class<?> propertyType) {
        Object propertyValue;
        if (Date.class.equals(propertyType)) {
            // 字符串转换为日期
            propertyValue = DateUtil.formatDateTimeStr(dataValue, DateUtil.EXCEL_DATE_TIME);
        } else if (Long.class.equals(propertyType)) {
            propertyValue = new Long(dataValue);
        } else if (BigDecimal.class.equals(propertyType)) {
            propertyValue = new BigDecimal(dataValue);
        } else if (BigInteger.class.equals(propertyType)) {
            propertyValue = new BigInteger(dataValue);
        } else if (Integer.class.equals(propertyType)) {
            propertyValue = new Integer(dataValue);
        } else if (Boolean.class.equals(propertyType)) {
            propertyValue = new Boolean(dataValue);
        } else {
            propertyValue = dataValue;
        }
        return propertyValue;
    }
}
