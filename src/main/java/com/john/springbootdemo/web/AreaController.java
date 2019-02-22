package com.john.springbootdemo.web;

import com.john.springbootdemo.entity.Area;
import com.john.springbootdemo.service.AreaService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2019/2/1 14:12
 * <p/>
 * Description:区域返回类逻辑
 */
 //返回json的注解
@RestController
@RequestMapping("/superadmin")
public class AreaController {

    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "/listarea", method = RequestMethod.GET)
    private Map<Object, Object> getListArea() {
        Map<Object, Object> modelMap = new HashMap<>();
        List<Area> areaList = areaService.queryArea();
        modelMap.put("code",200);
        modelMap.put("msg","success");
        modelMap.put("data", areaList);
        return modelMap;
    }

    @RequestMapping(value = "/getareabyid", method = RequestMethod.GET)
    private Map<Object, Object> getAreabyId(int areaId) {
        Map<Object, Object> modelMap = new HashMap<>();
        Area area = areaService.queryAreaById(areaId);
        modelMap.put("code",200);
        modelMap.put("msg","success");
        modelMap.put("data", area);
        return modelMap;
    }

    //没有将requestBody注解用上 后续再做探讨
    @RequestMapping(value = "/addarea", method = RequestMethod.POST)
    private Map<Object, Object> addArea(Area area) {
        Map<Object, Object> modelMap = new HashMap<>();
        modelMap.put("code",areaService.insertArea(area)?200:0);
        modelMap.put("msg","success");
        modelMap.put("data","插入成功");
        return modelMap;
    }
    //没有将requestBody注解用上 后续再做探讨
    @RequestMapping(value = "/modifyarea", method = RequestMethod.POST)
    private Map<Object, Object> modifyArea(Area area) {
        Map<Object, Object> modelMap = new HashMap<>();
        modelMap.put("code", areaService.modifyArea(area)?200:0);
        modelMap.put("msg","success");
        modelMap.put("data","修改成功");
        return modelMap;
    }

    @RequestMapping(value = "/removearea", method = RequestMethod.GET)
    private Map<Object, Object> removeArea(int areaId) {
        Map<Object, Object> modelMap = new HashMap<>();
        modelMap.put("code", areaService.deleteArea(areaId)?200:0);
        modelMap.put("msg","success");
        modelMap.put("data","删除成功");
        return modelMap;
    }


    @RequestMapping(value = "/addpagearea", method = RequestMethod.GET)
    private Map<Object, Object> addPageArea(int page,int size) {
        Map<Object, Object> modelMap = new HashMap<>();
        List<Area> areaList=areaService.queryPageArea(page,size);
        modelMap.put("code", 200);
        modelMap.put("msg","success");
        modelMap.put("data",areaList);
        return modelMap;
    }

}
