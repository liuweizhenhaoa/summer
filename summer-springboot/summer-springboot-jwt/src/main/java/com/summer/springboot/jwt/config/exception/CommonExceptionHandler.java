package com.summer.springboot.jwt.config.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@Slf4j
@Component
public class CommonExceptionHandler {

    private static final String CODE = "code";

    private static final String MSG = "msg";


    /**
     * 拦截Exception类的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Map<String, Object> exceptionHandler(Exception e) {
        log.error("error:{}", e);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(CODE, "-1");
        result.put(MSG, "error");
        //正常开发中，可创建一个统一响应实体，如CommonResp
        return result;
    }

    /**
     * 处理实体字段校验不通过异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, Object> validationError(MethodArgumentNotValidException ex) {
        log.error("raised MethodArgumentNotValidException : " + ex);
        BindingResult result = ex.getBindingResult();
        final List<FieldError> fieldErrors = result.getFieldErrors();
        StringBuilder builder = new StringBuilder();

        for (FieldError error : fieldErrors) {
            builder.append(error.getDefaultMessage() + "\n");
        }
        Map<String, Object> map = new HashMap<String, Object>();

        map.put(CODE, "-1");
        map.put(MSG, builder.toString());
        return map;
    }

    /**
     * 处理实体字段校验不通过异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, Object> validationError(BindException ex) {
        log.error("raised BindException : " + ex);
        BindingResult result = ex.getBindingResult();
        final List<FieldError> fieldErrors = result.getFieldErrors();
        StringBuilder builder = new StringBuilder();

        for (FieldError error : fieldErrors) {
            builder.append(error.getDefaultMessage() + "\n");
        }
        Map<String, Object> map = new HashMap<String, Object>();

        map.put(CODE, "-1");
        map.put(MSG, builder.toString());
        return map;
    }


//    /**
//     * 拦截 CommonException 的异常
//     * @param ex
//     * @return
//     */
//    @ExceptionHandler(BussinessException.class)
//    @ResponseBody
//    public Map<String,Object> exceptionHandler(BussinessException ex){
//        log.info("CommonException：{}({})",ex.getMsg(), ex.getCode());
//        Map<String,Object> result = new HashMap<String,Object>();
//        result.put("respCode", ex.getCode());
//        result.put("respMsg", ex.getMsg());
//        return result;
//    }
}