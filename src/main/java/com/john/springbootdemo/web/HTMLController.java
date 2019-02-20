package com.john.springbootdemo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2019/2/19 20:02
 * <p/>
 * Description:
 */
//访问HTML页面的注解
@Controller
public class HTMLController {

    @RequestMapping(value = "/mainpage")
    private String getmainPage(){
        return "index";
    }


}
