package com.john.springbootdemo.dao;

import com.john.springbootdemo.entity.Area;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.*;

/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2019/1/30 15:02
 * <p/>
 * Description:单元测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AreaDaoTest {

    @Autowired
    private AreaDao areaDao;

    @Value("SSSSS")
    private String username;

    @Test
    @Ignore
    public void queryArea() {//成功
        List<Area> areaList = areaDao.queryArea();
        assertEquals(2, areaList.size());
    }

    @Test
    @Ignore
    public void queryAreaById() {//成功
        Area area = areaDao.queryAreaById(2);
        assertEquals("南苑", area.getAreaName());
    }

    @Test
    @Ignore
    public void insertArea() {//成功 改动的行数是1
        Area area = new Area();
        area.setAreaName("西九苑");
        area.setPriority(1);
        area.setCreateTime(new Date());
        area.setLastEditTime(new Date());
        int i = areaDao.insertArea(area);
        assertEquals(1, i);
    }

    @Test
    @Ignore
    public void updateArea() {
        Area area = new Area();
        area.setAreaName("东南666苑");
        area.setAreaId(6);
        area.setLastEditTime(new Date());
        int i = areaDao.updateArea(area);
        assertEquals(1, i);
    }

    @Test
    @Ignore
    public void deleteArea() {
        int i = areaDao.deleteArea(3);
        assertEquals(1, i);
    }

    @Test
    @Ignore
    public void test1() {
        System.out.println("---------------" + username + "-------------");
    }


}