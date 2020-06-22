package com.liudehuang.common.enums;

import com.liudehuang.common.constant.ReturnCodeConstant;

/**
 * @Description:
 * @Author: liudh
 * @CreateDate: 2020/6/22 10:42
 * @UpdateUser: liudh
 * @UpdateDate: 2020/6/22 10:42
 * @UpdateRemark:
 * @Version:
 */
public enum ReturnCodeEnum {
    /**
     * 接口返回值: 数据处理成功[1000] -- 列表查询时无数据返回也使用此编码
     */
    TYPE_1000(ReturnCodeConstant.CODE_1000, "成功"),

    /**
     * 接口返回值: 接口访问受权限限制不通过[1001]
     */
    TYPE_1001(ReturnCodeConstant.CODE_1001, "访问受限"),

    /**
     *接口返回值: 数据信息不存在[1002] -- 数据库中查询不到指定的数据(非列表查询)
     */
    TYPE_1002(ReturnCodeConstant.CODE_1002, "数据信息不存在"),

    /**
     * 接口返回值: 数据错误,数据规则校验不通过,数据不符合接口规则[1003]
     */
    TYPE_1003(ReturnCodeConstant.CODE_1003, "数据错误,不符合限定规则"),

    /**
     * 接口返回值: 服务器处理异常[1004]
     */
    TYPE_1004(ReturnCodeConstant.CODE_1004, "服务器处理异常"),

    /**
     * 接口返回值: 数据处理失败[1005] (如: 保存、发送等服务内部操作)
     */
    TYPE_1005(ReturnCodeConstant.CODE_1005, "数据处理失败"),

    /**
     * 接口返回值: 用户输入或接口入参缺少必要参数[1006]
     */
    TYPE_1006(ReturnCodeConstant.CODE_1006, "参数不全"),

    /**
     * 接口返回值: 系统服务间通讯异常[1007]
     */
    TYPE_1007(ReturnCodeConstant.CODE_1007, "请求外部服务通讯异常"),

    /**
     * 接口返回值: 数据重复[1008]
     */
    TYPE_1008(ReturnCodeConstant.CODE_1008, "数据信息已存在"),

    /**
     * 接口返回值: 客户端TOKEN验证不通过或用户登录账户相关问题[1009]
     */
    TYPE_1009(ReturnCodeConstant.CODE_1009, "登录状态已失效, 请重新登录"),

    /**
     * 接口返回值: 短信或其它的验证码验证失败[1010]
     */
    TYPE_1010(ReturnCodeConstant.CODE_1010, "验证码验证失败"),

    /**
     * 接口返回值: 数据处理中[1011]
     */
    TYPE_1011(ReturnCodeConstant.CODE_1011, "数据处理中")
    ;

    private Integer code;

    private String message;

    ReturnCodeEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String getMessageByCode(Integer code){
        if(code == null){
            return null;
        }
        for(ReturnCodeEnum returnCodeEnum : values()){
            if(code.equals(returnCodeEnum.getCode())){
                return returnCodeEnum.getMessage();
            }
        }
        return null;
    }

}
