package com.john.springbootdemo.result;

/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2019/2/28 19:02
 * <p/>
 * Description:
 */
public class HttpResult<T> {

    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

}
