package com.john.springbootdemo.web;

import com.john.springbootdemo.entity.Area;
import com.john.springbootdemo.result.HttpResult;
import com.john.springbootdemo.result.ResultUtil;
import com.john.springbootdemo.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private AreaService areaService;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @RequestMapping(value = "/testKey", method = RequestMethod.GET)
    private String testKey(String key) {
        if (stringRedisTemplate.hasKey(key)) {
            String value = stringRedisTemplate.opsForValue().get(key);
            return "从redis缓存的值" + value;
        } else {
            return "key不存在 请先保存";
        }

    }

    @RequestMapping(value = "/setKey", method = RequestMethod.GET)
    private String setKey(String key) {
        if (stringRedisTemplate.hasKey(key)) {
            return "这个key已经存在了，请重新设置key";
        }
        stringRedisTemplate.opsForValue().set(key, "Hello Redis");
        return "缓存到Redis成功";
    }

    /**
     * 优先取redis缓存；没有则取数据库，并将key和value设置到Redis
     */
    @RequestMapping(value = "/getareabyid", method = RequestMethod.GET)
    private HttpResult<Area> getAreabyId(int areaId) {
        if(areaId>0){
            String key="Area_id"+areaId;
            if(redisTemplate.hasKey(key)){//如果有则取缓存
                Object o=redisTemplate.opsForValue().get(key);
                if(o instanceof  HttpResult){
                    HttpResult<Area> httpResult=(HttpResult) o;
                    httpResult.setMsg("来自Redis缓存");
                    return httpResult;
                }
            }
            HttpResult<Area> httpResult=areaService.queryAreaById(areaId);
            redisTemplate.opsForValue().set(key,httpResult);
            httpResult.setMsg("来自MySql数据库");
            return httpResult;
        }else {
            return ResultUtil.errorPromote("区域ID必须大于0");
        }
    }


}
