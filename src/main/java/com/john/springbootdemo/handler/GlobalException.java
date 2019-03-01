package com.john.springbootdemo.handler;

import com.john.springbootdemo.result.HttpResult;
import com.john.springbootdemo.result.ResultUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

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
    public <T> HttpResult<T> exceptionHandler(HttpServletRequest request, Exception e) {
        if (e instanceof PromoteException) {
            PromoteException promoteException = (PromoteException) e;
            return ResultUtil.errorPromote(promoteException.getCode(), e.getMessage());
        }
        return ResultUtil.errorPromote(-1, e.getMessage());
    }


}
