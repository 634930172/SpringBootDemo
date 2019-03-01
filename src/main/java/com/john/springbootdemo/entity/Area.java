package com.john.springbootdemo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2019/1/29 16:15
 * <p/>
 * Description:开启表单验证
 */
public class Area {

    @Min(value = 0,message ="区域ID信息必须大于0")
    private int areaId;

    @NotNull(message = "区域名不能为空")
    private String areaName;

    @Min(value = 1,message = "权重必须大于1")
    private int priority;

    //定义时间格式的类型 防止从数据库取出的时间有小数点等其他格式
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastEditTime;


    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

}
