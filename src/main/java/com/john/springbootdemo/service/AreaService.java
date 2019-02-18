package com.john.springbootdemo.service;

import com.john.springbootdemo.entity.Area;

import java.util.List;

/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2019/2/1 11:27
 * <p/>
 * Description:
 */
public interface AreaService {

    List<Area> queryArea();

    Area queryAreaById(int areaId);

    boolean insertArea(Area area);

    boolean modifyArea(Area area);

    boolean deleteArea(int  areaId);


}
