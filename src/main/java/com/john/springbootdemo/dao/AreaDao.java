package com.john.springbootdemo.dao;

import com.john.springbootdemo.entity.Area;

import java.util.List;

/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2019/1/30 13:45
 * <p/>
 * Description:
 */
public interface AreaDao {

    List<Area>  queryArea();

    Area queryAreaById(int areaId);

    int insertArea(Area area);

    int updateArea(Area area);

    int deleteArea(int  areaId);

}
