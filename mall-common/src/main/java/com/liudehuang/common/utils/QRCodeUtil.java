package com.liudehuang.common.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: liudh
 * @CreateDate: 2020/6/22 10:26
 * @UpdateUser: liudh
 * @UpdateDate: 2020/6/22 10:26
 * @UpdateRemark:
 * @Version:
 */
public class QRCodeUtil {
    protected QRCodeUtil() {
    }

    public static BitMatrix deleteWhite(BitMatrix matrix, int margin) {
        int tempM = margin * 2;
        int[] rec = matrix.getEnclosingRectangle();
        int resWidth = rec[2] + tempM;
        int resHeight = rec[3] + tempM;
        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();

        for(int i = margin; i < resWidth - margin; ++i) {
            for(int j = margin; j < resHeight - margin; ++j) {
                if (matrix.get(i - margin + rec[0], j - margin + rec[1])) {
                    resMatrix.set(i, j);
                }
            }
        }

        return resMatrix;
    }

    public static byte[] createQr(String url, int width, int height, String format) {
        ByteArrayOutputStream swapStream = null;
        Object var5 = null;

        byte[] var8;
        try {
            Map<EncodeHintType, Object> hints = new HashMap();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix bitMatrix = (new MultiFormatWriter()).encode(url, BarcodeFormat.QR_CODE, width, height, hints);
            bitMatrix = deleteWhite(bitMatrix, 10);
            swapStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, format, swapStream);
            byte[] data = swapStream.toByteArray();
            var8 = data;
        } catch (Exception var17) {
            throw new RuntimeException(var17);
        } finally {
            if (null != swapStream) {
                try {
                    swapStream.close();
                } catch (IOException var16) {
                    swapStream = null;
                }
            }

        }

        return var8;
    }
}
