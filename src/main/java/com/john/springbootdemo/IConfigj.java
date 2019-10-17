package com.john.springbootdemo;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2019/9/19 14:24
 * <p/>
 * Description:
 */
public class IConfigj implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = (String) request.getAttribute("token");
        //验证token
        //获取用户身份redis
        //j将用户身份带人参数
        request.setAttribute("userId","xxx");

        return false;
    }
}
