package com.liudehuang.common.constant;

/**
 * @Description:
 * @Author: liudh
 * @CreateDate: 2020/6/22 10:20
 * @UpdateUser: liudh
 * @UpdateDate: 2020/6/22 10:20
 * @UpdateRemark:
 * @Version:
 */
public class AESConstant extends AlgorithmConstant {
    public static final Integer KEY_SIZE_128 = 128;
    public static final Integer KEY_SIZE_192 = 192;
    public static final Integer KEY_SIZE_256 = 256;
    public static final Integer KEY_OFFSET = 16;
    public static final String ECB_PKCS5_PADDING = "AES/ECB/PKCS5Padding";
    public static final String CBC_PKCS5_PADDING = "AES/CBC/PKCS5Padding";

    protected AESConstant() {
    }
}