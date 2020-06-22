package com.liudehuang.common.controller;

import com.liudehuang.common.constant.ReturnCodeConstant;
import com.liudehuang.common.enums.ReturnCodeEnum;
import com.liudehuang.common.exception.*;
import com.liudehuang.common.response.Response;
import com.liudehuang.common.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @Description:
 * @Author: liudh
 * @CreateDate: 2020/6/22 11:02
 * @UpdateUser: liudh
 * @UpdateDate: 2020/6/22 11:02
 * @UpdateRemark:
 * @Version:
 */
@Slf4j
@ControllerAdvice
public abstract class BaseController {

    /**
     * 异常拦截处理
     *
     * @param exp
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.OK)
    public Response handleException(Exception exp) {
        // TODO: error log
        log.error("服务处理异常  timeStamp: {} msg: {}", System.currentTimeMillis(), exp);
        return ResponseUtil.setResponse(ReturnCodeEnum.TYPE_1004, Response.class);
    }

    /**
     * 服务间通讯请求异常拦截处理
     *
     * @param exp
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler(HttpRequestException.class)
    public Response handleHttpRequestException(HttpRequestException exp) {
        // TODO: error log
        log.error("服务间通讯请求异常  timeStamp: {} msg: {}", System.currentTimeMillis(), exp);
        return ResponseUtil.setResponse(ReturnCodeConstant.CODE_1007, exp.getMessage(), Response.class);
    }

    /**
     * 校验异常拦截处理
     *
     * @param exp
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler({ValidationException.class})
    public Response handleValidateException(ValidationException exp) {
        if (log.isDebugEnabled()) {
            log.debug("Object 属性校验错误 msg: {}", exp.getMessage());
        }
        return ResponseUtil.setResponse(ReturnCodeConstant.CODE_1006, exp.getMessage(), Response.class);
    }

    /**
     * Method Argument校验异常拦截处理
     *
     * @param exp
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Response handleMethodArgumentNoValidException(MethodArgumentNotValidException exp) {
        String defaultMessage = exp.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        if (log.isDebugEnabled()) {
            log.debug("Method Argument校验错误 msg: {}", defaultMessage);
        }
        return ResponseUtil.setResponse(ReturnCodeConstant.CODE_1006, defaultMessage, Response.class);
    }

    /**
     * 数据加密/解密操作异常拦截处理
     *
     * @param exp
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler({CodecException.class})
    public Response handleCodecException(CodecException exp) {
        // TODO: error log
        log.error("数据Codec转换解析处理异常 msg: {}", exp.getMessage());
        return ResponseUtil.setResponse(ReturnCodeConstant.CODE_1004,
                "数据Codec转换解析处理异常", Response.class);
    }

    /**
     * 生成分布式主键KEY过程异常拦截处理
     *
     * @param exp
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler({IdGeneratorException.class})
    public Response handleIdGeneratorException(IdGeneratorException exp) {
        // TODO: error log
        log.error("生成主键KEY过程中处理异常 msg: {}", exp.getMessage());
        return ResponseUtil.setResponse(ReturnCodeConstant.CODE_1004,
                "生成主键KEY过程中处理异常", Response.class);
    }

    /**
     * 自定义异常拦截处理
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler({BusinessException.class})
    public Response businessException(BusinessException e) {
        log.error(e.getMessage());
        Integer code = e.getCode();
        if (null == code) {
            code = ReturnCodeConstant.CODE_1005;
        }
        return ResponseUtil.setResponse(code, e.getMessage(), Response.class);
    }

    /**
     * 认证异常处理
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler({AuthenticationException.class})
    public Response authenticationException(AuthenticationException e) {
        log.error(e.getMessage(), e);
        return ResponseUtil.setResponse(ReturnCodeEnum.TYPE_1009, Response.class);
    }

    /**
     * 授权异常处理
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler({AuthorizationException.class})
    public Response authorizationException(AuthorizationException e) {
        log.error(e.getMessage(), e);
        return ResponseUtil.setResponse(ReturnCodeEnum.TYPE_1001, Response.class);
    }


}