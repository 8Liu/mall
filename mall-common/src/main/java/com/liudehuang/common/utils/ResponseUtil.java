package com.liudehuang.common.utils;

import com.liudehuang.common.constant.ReturnCodeConstant;
import com.liudehuang.common.enums.ReturnCodeEnum;
import com.liudehuang.common.request.BasePageParam;
import com.liudehuang.common.response.BaseResponse;
import com.liudehuang.common.response.PageResponse;
import com.liudehuang.common.response.Response;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Description:
 * @Author: liudh
 * @CreateDate: 2020/6/22 10:51
 * @UpdateUser: liudh
 * @UpdateDate: 2020/6/22 10:51
 * @UpdateRemark:
 * @Version:
 */
public class ResponseUtil {
    protected ResponseUtil() {

    }

    /**
     * 获取 Response
     */
    public static Response setResponse(Integer returnCode) {
        return setResponse(returnCode, ReturnCodeEnum.getMessageByCode(returnCode), Response.class);
    }

    /**
     * 获取 Response
     *
     * @param returnCodeEnum
     * @return
     */
    public static Response setResponse(ReturnCodeEnum returnCodeEnum) {
        return setResponse(returnCodeEnum.getCode(), returnCodeEnum.getMessage(), Response.class);
    }

    /**
     * 获取 Response
     *
     * @param returnCode
     * @return
     */
    public static Response setResponse(Integer returnCode, String message) {
        return setResponse(returnCode, message, Response.class);
    }

    /**
     * 获取 Response
     *
     * @param returnType
     * @return
     */
    public static Response setResponse(ReturnCodeEnum returnType, String message) {
        return setResponse(returnType.getCode(), message, Response.class);
    }

    /**
     * 设置数据处理操作返回信息
     *
     * @param disposeFlag
     * @return
     */
    public static Response setDisposeResponse(Integer disposeFlag) {
        return setDisposeResponse(disposeFlag, null);
    }

    /**
     * 设置数据处理操作返回信息
     *
     * @param disposeFlag
     * @param failedMsg
     * @return
     */
    public static Response setDisposeResponse(Integer disposeFlag, String failedMsg) {
        // 返回信息封装处理
        if (disposeFlag > 0) {
            return setResponse(ReturnCodeEnum.TYPE_1000);
        } else {
            return setResponse(ReturnCodeConstant.CODE_1005, failedMsg);
        }
    }

    /**
     * 获取 BaseResponse
     */
    public static BaseResponse setBaseResponse(Integer returnCode) {
        return setResponse(returnCode, ReturnCodeEnum.getMessageByCode(returnCode), BaseResponse.class);
    }

    /**
     * 获取 BaseResponse
     *
     * @param returnCodeEnum
     * @return
     */
    public static BaseResponse setBaseResponse(ReturnCodeEnum returnCodeEnum) {
        return setResponse(returnCodeEnum.getCode(), returnCodeEnum.getMessage(), BaseResponse.class);
    }

    /**
     * 获取 BaseResponse
     *
     * @param returnCode
     * @return
     */
    public static BaseResponse setBaseResponse(Integer returnCode, String message) {
        return setResponse(returnCode, message, BaseResponse.class);
    }

    /**
     * 获取 BaseResponse
     *
     * @param returnCodeEnum
     * @return
     */
    public static BaseResponse setBaseResponse(ReturnCodeEnum returnCodeEnum, String message) {
        return setResponse(returnCodeEnum.getCode(), message, BaseResponse.class);
    }

    /**
     * 设置数据处理操作返回信息
     *
     * @param disposeFlag
     * @return
     */
    public static BaseResponse setDisposeDataResponse(Integer disposeFlag) {
        return setDisposeDataResponse(disposeFlag, null, null);
    }

    /**
     * 设置数据处理操作返回信息
     *
     * @param disposeFlag
     * @param failedMsg
     * @return
     */
    public static BaseResponse setDisposeDataResponse(Integer disposeFlag, String failedMsg) {
        return setDisposeDataResponse(disposeFlag, failedMsg, null);
    }

    /**
     * 设置数据处理操作返回信息
     *
     * @param disposeFlag
     * @param failedMsg
     * @param dataInfo
     * @return
     */
    public static <T> BaseResponse<T> setDisposeDataResponse(Integer disposeFlag, String failedMsg, T dataInfo) {
        // 返回信息封装处理
        if (disposeFlag > 0) {
            BaseResponse<T> response = setBaseResponse(ReturnCodeEnum.TYPE_1000);
            if (null != response && null != dataInfo) {
                response.setDataInfo(dataInfo);
            }
            return response;
        } else {
            return setBaseResponse(ReturnCodeConstant.CODE_1005, failedMsg);
        }
    }

    /**
     * 封装数据信息返回对象
     *
     * @param dataInfo
     * @return
     */
    public static <T> BaseResponse<T> setResponseData(T dataInfo) {
        BaseResponse<T> response;
        if (null == dataInfo) {
            response = setBaseResponse(ReturnCodeEnum.TYPE_1002);
        } else {
            response = setBaseResponse(ReturnCodeEnum.TYPE_1000);
            if (null != response) {
                response.setDataInfo(dataInfo);
            }
        }
        return response;
    }

    /**
     * 封装List列表返回对象信息
     *
     * @param dataList
     * @return
     */
    public static <T> BaseResponse<List<T>> setListResponseData(List<T> dataList) {
        BaseResponse<List<T>> response;
        if (CollectionUtils.isEmpty(dataList)) {
            response = setBaseResponse(ReturnCodeEnum.TYPE_1002);
        } else {
            response = setBaseResponse(ReturnCodeEnum.TYPE_1000);
            if (null != response) {
                response.setDataInfo(dataList);
            }
        }
        return response;
    }

    /**
     * 设置分页返回数据信息
     *
     * @param dataList   数据列表
     * @param dataTotal  数据总记录数
     * @param queryParam 分页查询条件
     * @return
     */
    public static <T> PageResponse<T> setPageResponseData(List<T> dataList,
                                                          Integer dataTotal,
                                                          BasePageParam queryParam) {
        return setPageResponseData(dataList, dataTotal, queryParam.getPageNum(), queryParam.getPageSize());
    }

    /**
     * 设置分页返回数据信息
     *
     * @param dataList  数据列表
     * @param dataTotal 数据总记录数
     * @param pageNum   当前查询页码
     * @param pageSize  每页查询记录数
     * @return
     */
    public static <T> PageResponse<T> setPageResponseData(List<T> dataList,
                                                          Integer dataTotal,
                                                          Integer pageNum,
                                                          Integer pageSize) {
        PageResponse<T> pageData = new PageResponse<T>();
        pageData.setCurrent(pageNum);
        pageData.setSize(pageSize);
        pageData.setRecords(dataList);
        pageData.setTotal(dataTotal);
        pageData.setReturnCode(ReturnCodeConstant.CODE_1000);
        return pageData;
    }

    /**
     * 获取 Response
     *
     * @param returnType
     * @return
     */
    public static <T extends Response> T setResponse(ReturnCodeEnum returnType, Class<T> responseClass) {
        return setResponse(returnType.getCode(), returnType.getMessage(), responseClass);
    }

    /**
     * 获取 Response
     *
     * @param returnType
     * @return
     */
    public static <T extends Response> T setResponse(ReturnCodeEnum returnType, String message, Class<T> responseClass) {
        return setResponse(returnType.getCode(), StringUtils.isEmpty(message) ? returnType.getMessage() : message, responseClass);
    }

    /**
     * 获取 Response
     *
     * @param returnCode
     * @return
     */
    public static <T extends Response> T setResponse(Integer returnCode, String message, Class<T> responseClass) {
        T baseResponse = null;
        if (null != responseClass) {
            try {
                baseResponse = responseClass.newInstance();
                baseResponse.setReturnCode(returnCode);
                baseResponse.setMessage(message);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return baseResponse;
    }

    /**
     * 判断是否是成功请求
     */
    public static boolean isOk(Response response) {
        return null != response && ReturnCodeConstant.CODE_1000.equals(response.getReturnCode());
    }

    /**
     * 根据response获取新的response
     */
    public static BaseResponse get(Response response) {
        return setBaseResponse(response.getReturnCode(), response.getMessage());
    }
}
