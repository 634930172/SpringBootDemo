package com.john.springbootdemo.web;

import com.john.springbootdemo.entity.Area;
import com.john.springbootdemo.result.HttpResult;
import com.john.springbootdemo.service.AreaService;
import com.john.springbootdemo.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2019/8/12 14:26
 * <p/>
 * Description:
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private StringRedisTemplate template;


    @RequestMapping(value = "/testKey", method = RequestMethod.GET)
    private String testKey(String key) {
        if (template.hasKey(key)) {
            String value = template.opsForValue().get(key);
            return "从redis缓存的值" + value;
        } else {
            return "key不存在 请先保存";
        }

    }

    @RequestMapping(value = "/setKey", method = RequestMethod.GET)
    private String setKey(String key) {
        if (template.hasKey(key)) {
            return "这个key已经存在了，请重新设置key";
        }
        template.opsForValue().set(key, "Hello Redis");
        return "缓存到Redis成功";
    }


}
