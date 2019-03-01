package com.john.springbootdemo.enums;

/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2019/2/28 19:34
 * <p/>
 * Description:
 */
public enum ResultEnum {
    UNKONW_ERROR(-1,"未知错误"),
    SUCCESS(200,"成功"),
    INSERT_FAILED(400, "数据库插入信息失败"),
    AREA_IS_EMPTY(400, "信息为空"),
    DATA_EMPTY(400,"返回数据为空"),
    AREA_ID_MUST_NOT_NULL(400,"信息ID必须大于0"),
    UPDATE_FAILED(400,"数据库更新失败"),
    DATE_EMPTY_OR_ID_MIN_0(400,"信息为空或ID小于0"),
    DELETE_FAILED(400,"数据库删除失败"),
    QUERY_FAILED(400,"查询失败"),
    PAGE_MUST_MORE_0(400,"页数必须大于0");


    private int code;
    private String msg;

    ResultEnum(int code, String msg) {
        this.code=code;
        this.msg=msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
