package com.john.springbootdemo.result;

/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2019/2/28 19:05
 * <p/>
 * Description:返回结果工具类
 */
public class ResultUtil {

    public static <T>  HttpResult<T> retrunData(T data){
        HttpResult<T> result=new HttpResult<>();
        result.setCode(200);
        result.setMsg("success");
        result.setData(data);
        return result;
    }


    public static <T>  HttpResult<T> errorPromote(int code,String msg){
        HttpResult<T> result=new HttpResult<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    public static <T>  HttpResult<T> errorPromote(String msg){
        HttpResult<T> result=new HttpResult<>();
        result.setCode(400);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    public static <T>  HttpResult<T> retrunMsg(String msg){
        HttpResult<T> result=new HttpResult<>();
        result.setCode(200);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    public static <T>  HttpResult<T> operateSuccess(){
        HttpResult<T> result=new HttpResult<>();
        result.setCode(200);
        result.setMsg("执行成功");
        result.setData(null);
        return result;
    }
}
