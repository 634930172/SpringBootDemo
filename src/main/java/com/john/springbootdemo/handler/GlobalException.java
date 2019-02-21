package com.john.springbootdemo.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2019/2/1 15:41
 * <p/>
 * Description:异常处理类
 */
@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Map<Object,Object> exceptionHandler( HttpServletRequest request,Exception e){
        Map<Object, Object> modelMap = new HashMap<>();
        modelMap.put("code",400);
        modelMap.put("msg",e.getMessage());
        modelMap.put("data",null);
        return modelMap;
    }


}
