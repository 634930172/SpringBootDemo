package com.john.springbootdemo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2019/1/29 15:33
 * <p/>
 * Description:
 */
@RestController
public class Hello {

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hello(){
        return "Hello SpringBoot";
    }


}
