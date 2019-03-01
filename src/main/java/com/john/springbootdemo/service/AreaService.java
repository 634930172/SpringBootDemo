package com.john.springbootdemo.service;

import com.john.springbootdemo.entity.Area;
import com.john.springbootdemo.result.HttpResult;

import java.util.List;

/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2019/2/1 11:27
 * <p/>
 * Description:
 */
public interface AreaService {

    HttpResult<List<Area>> queryArea();

    HttpResult<Area> queryAreaById(int areaId);

    HttpResult<String> insertArea(Area area);

    HttpResult<String> modifyArea(Area area);

    HttpResult<String> deleteArea(int  areaId);

    HttpResult<List<Area>> queryPageArea(int index,int size);


}
