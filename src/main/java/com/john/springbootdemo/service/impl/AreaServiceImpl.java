package com.john.springbootdemo.service.impl;

import com.john.springbootdemo.dao.AreaDao;
import com.john.springbootdemo.entity.Area;
import com.john.springbootdemo.service.AreaService;
import com.mysql.jdbc.StringUtils;
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
    public List<Area> queryArea() {
        return areaDao.queryArea();
    }

    @Override
    public Area queryAreaById(int areaId) {
        if (areaId > 0) {
            try {
                Area area = areaDao.queryAreaById(areaId);
                if (area != null) {
                    return area;
                } else {
                    throw new RuntimeException("区域返回数据为空----");
                }
            } catch (Exception e) {
                throw new RuntimeException("返回区域错误：" + e.getMessage());
            }
        } else {
            throw new RuntimeException("区域ID必须大于0");
        }
    }

    @Transactional
    @Override
    public boolean insertArea(Area area) {
        if (!StringUtils.isNullOrEmpty(area.getAreaName())) {
            area.setCreateTime(new Date());
            area.setLastEditTime(new Date());
            try {
                int i = areaDao.insertArea(area);
                if (i > 0) {
                    return true;
                } else {
                    throw new RuntimeException("插入区域信息失败失败");
                }
            } catch (Exception e) {
                throw new RuntimeException("插入失败：" + e.getMessage());
            }
        } else {
            throw new RuntimeException("区域信息为空");
        }
    }

    @Transactional
    @Override
    public boolean modifyArea(Area area) {
        if (area != null && area.getAreaId() > 0) {
            area.setLastEditTime(new Date());
            try {
                int i = areaDao.updateArea(area);
                if (i > 0) {
                    return true;
                } else {
                    throw new RuntimeException("更新数据失败");
                }
            } catch (Exception e) {
                throw new RuntimeException("更新数据失败：" + e.getMessage());
            }
        } else {
            throw new RuntimeException("区域信息不能为空");
        }
    }

    @Override
    public boolean deleteArea(int areaId) {
        if (areaId > 0) {
            try {
                int i = areaDao.deleteArea(areaId);
                if (i > 0) {
                    return true;
                }else {
                    throw new RuntimeException("删除区域信息失败");
                }
            } catch (Exception e) {
                    throw  new RuntimeException("删除区域信息失败："+e.getMessage());
            }
        } else {
            throw new RuntimeException("区域ID必须大于0");
        }
    }

    /**
     * 分页加载 暂时用java代码判断
     */
    @Override
    public List<Area> queryPageArea(int page,int size) {
        if(page>=0){
            try {
                int reaPage=(page-1)*3;
                if(reaPage<0){
                    reaPage=0;
                }
                if(size==0){
                    size=3;
                }
                List<Area> areas= areaDao.queryPageArea(reaPage,size);
              if(areas!=null){
                  return areas;
              }else {
                  throw new RuntimeException("查询失败");
              }
            }catch (Exception e){
                throw  new RuntimeException("分页抛出异常："+e.getMessage());
            }
        }else {
            throw new RuntimeException("页数必须大于0");
        }
    }
}
