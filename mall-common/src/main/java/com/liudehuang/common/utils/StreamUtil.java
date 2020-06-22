package com.liudehuang.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

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
public class StreamUtil {

    /**
     * 写输出流
     *
     * @param inputStream
     * @param outputStream
     * @throws IOException
     */
    public static void writeOutputStream(InputStream inputStream, OutputStream outputStream)
            throws IOException {
        // 实现文件下载常规操作
        byte[] buffer = new byte[1024];
        int readTmp;
        while ((readTmp = inputStream.read(buffer)) != -1) {
            // 并不是每次都能读到1024个字节, 所有用readTmp作为每次读取数据的长度, 否则会出现文件损坏的错误
            outputStream.write(buffer, 0, readTmp);
        }
    }

    /**
     * 关闭输入流
     *
     * @param inputStream
     */
    public static void closeInputStream(InputStream inputStream) {
        try {
            if (null != inputStream) {
                inputStream.close();
            }
        } catch (IOException e) {
            log.error("关闭输入流操作异常", e);
        }
    }

    /**
     * 关闭输出流
     *
     * @param outputStream
     */
    public static void closeOutputStream(OutputStream outputStream) {
        try {
            if (null != outputStream) {
                outputStream.close();
            }
        } catch (IOException e) {
            log.error("关闭输出流操作异常", e);
        }
    }

    /**
     * 关闭文件写
     *
     * @param writer
     */
    public static void closeWriter(Writer writer) {
        try {
            if (null != writer) {
                writer.close();
            }
        } catch (IOException e) {
            log.error("关闭文件写异常", e);
        }
    }

    /**
     * 关闭文件读
     *
     * @param reader
     */
    public static void closeReader(Reader reader) {
        try {
            if (null != reader) {
                reader.close();
            }
        } catch (IOException e) {
            log.error("关闭文件读异常", e);
        }
    }
}