package com.john.springbootdemo.web;

import com.john.springbootdemo.entity.Area;
import com.john.springbootdemo.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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
@RestController
@RequestMapping("/superadmin")
public class AreaController {

    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "/listarea", method = RequestMethod.GET)
    private Map<Object, Object> getListArea() {
        Map<Object, Object> modelMap = new HashMap<>();
        List<Area> areaList = areaService.queryArea();
        modelMap.put("areaList", areaList);
        return modelMap;
    }

    @RequestMapping(value = "/getareabyid", method = RequestMethod.GET)
    private Map<Object, Object> getAreabyId(int areaId) {
        Map<Object, Object> modelMap = new HashMap<>();
        Area area = areaService.queryAreaById(areaId);
        modelMap.put("area", area);
        return modelMap;
    }

    @RequestMapping(value = "/addarea", method = RequestMethod.POST)
    private Map<Object, Object> addArea(@RequestBody Area area) {
        Map<Object, Object> modelMap = new HashMap<>();
        modelMap.put("success", areaService.insertArea(area));
        return modelMap;
    }

    @RequestMapping(value = "/modifyarea", method = RequestMethod.POST)
    private Map<Object, Object> modifyArea(@RequestBody Area area) {
        Map<Object, Object> modelMap = new HashMap<>();
        modelMap.put("success", areaService.modifyArea(area));
        return modelMap;
    }

    @RequestMapping(value = "/removearea", method = RequestMethod.GET)
    private Map<Object, Object> removeArea(int areaId) {
        Map<Object, Object> modelMap = new HashMap<>();
        modelMap.put("success", areaService.deleteArea(areaId));
        return modelMap;
    }

}
