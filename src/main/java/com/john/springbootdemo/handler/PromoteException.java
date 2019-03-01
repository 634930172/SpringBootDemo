package com.john.springbootdemo.handler;

import com.john.springbootdemo.enums.ResultEnum;

/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2019/2/28 19:28
 * <p/>
 * Description:提示用户异常信息处理类
 */
public class PromoteException extends RuntimeException {

    private int code;

    public PromoteException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code=resultEnum.getCode();
    }

    public int getCode() {
        return code;
    }
}
