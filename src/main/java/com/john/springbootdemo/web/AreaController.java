package com.john.springbootdemo.web;

import com.john.springbootdemo.entity.Area;
import com.john.springbootdemo.result.HttpResult;
import com.john.springbootdemo.result.ResultUtil;
import com.john.springbootdemo.service.AreaService;
import com.john.springbootdemo.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

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
    private HttpResult<List<Area>> getListArea() {
        LogUtil.log("查询开始");
        return areaService.queryArea();
    }

    @RequestMapping(value = "/getareabyid", method = RequestMethod.GET)
    private HttpResult<Area> getAreabyId(int areaId) {
        if(areaId>0){
            return areaService.queryAreaById(areaId);
        }else {
            return ResultUtil.errorPromote("区域ID必须大于0");
        }
    }

    //没有将requestBody注解用上 后续再做探讨
    @RequestMapping(value = "/addarea", method = RequestMethod.POST)
    private HttpResult<String> addArea(@Valid Area area, BindingResult result) {
        if (result.hasErrors()) {//验证表单
            return ResultUtil.errorPromote(result.getFieldError().getDefaultMessage());
        }
        return areaService.insertArea(area);
    }

    //没有将requestBody注解用上 后续再做探讨
    @RequestMapping(value = "/modifyarea", method = RequestMethod.POST)
    private HttpResult<String> modifyArea(@Valid Area area, BindingResult result) {
        if (result.hasErrors()) {//验证表单
            return ResultUtil.errorPromote(result.getFieldError().getDefaultMessage());
        }
        return areaService.modifyArea(area);
    }

    @RequestMapping(value = "/removearea", method = RequestMethod.GET)
    private HttpResult<String> removeArea(int areaId) {
        if(areaId>0){
            return areaService.deleteArea(areaId);
        }else {
            return ResultUtil.errorPromote("信息Id必须大于0");
        }
    }


    @RequestMapping(value = "/addpagearea", method = RequestMethod.GET)
    private HttpResult<List<Area>> addPageArea(int page, int size) {
        if(page>0){
            if (size < 0) {
                size = 3;
            }
            int index = (page - 1) * size;
            return areaService.queryPageArea(index, size);
        }else {
            return ResultUtil.errorPromote("最小页数为1");
        }
    }

}
