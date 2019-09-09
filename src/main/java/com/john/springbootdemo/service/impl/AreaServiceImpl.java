package com.john.springbootdemo.service.impl;

import com.john.springbootdemo.dao.AreaDao;
import com.john.springbootdemo.entity.Area;
import com.john.springbootdemo.enums.ResultEnum;
import com.john.springbootdemo.handler.PromoteException;
import com.john.springbootdemo.result.HttpResult;
import com.john.springbootdemo.result.ResultUtil;
import com.john.springbootdemo.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2019/2/1 11:29
 * <p/>
 * Description:
 */
@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaDao areaDao;

    @Override
    public HttpResult<List<Area>> queryArea() {
        return ResultUtil.retrunData(areaDao.queryArea());
    }

    @Override
    public HttpResult<Area> queryAreaById(int areaId) {
        return ResultUtil.retrunData(areaDao.queryAreaById(areaId));
    }

    @Transactional
    @Override
    public HttpResult<String> insertArea(Area area) {
        area.setCreateTime(new Date());
        area.setLastEditTime(new Date());
        int i = areaDao.insertArea(area);
        if (i > 0) {
            return ResultUtil.operateSuccess();
        } else {
            throw new PromoteException(ResultEnum.INSERT_FAILED);
        }
    }

    @Transactional
    @Override
    public HttpResult<String> modifyArea(Area area) {
        area.setLastEditTime(new Date());
        int i = areaDao.updateArea(area);
        if (i > 0) {
            return ResultUtil.operateSuccess();
        } else {
            throw new PromoteException(ResultEnum.UPDATE_FAILED);
        }
    }

    @Override
    public HttpResult<String> deleteArea(int areaId) {
        int i = areaDao.deleteArea(areaId);
        if (i > 0) {
            return ResultUtil.operateSuccess();
        } else {
            throw new PromoteException(ResultEnum.DELETE_FAILED);
        }
    }

    /**
     * 分页加载 暂时用java代码判断
     */
    @Override
    public HttpResult<List<Area>> queryPageArea(int index, int offset) {
        List<Area> areas = areaDao.queryPageArea(index, offset);
        if (areas != null && !areas.isEmpty()) {
            return ResultUtil.retrunData(areas);
        } else {
            throw new PromoteException(ResultEnum.QUERY_FAILED);
        }
    }
}
