package com.atguigu.jxc.service.impl;

import com.atguigu.jxc.dao.ReturnListDao;
import com.atguigu.jxc.entity.ReturnList;
import com.atguigu.jxc.service.ReturnListService;
import com.atguigu.jxc.vo.DateParam;
import com.atguigu.jxc.vo.ReturnGoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReturnListServiceImpl implements ReturnListService {

    @Autowired
    private ReturnListDao returnListDao;

    @Override
    public List<ReturnList> getSaleDataByDay(DateParam dataVo) {
        ReturnGoodsVo returnGoodsVo = new ReturnGoodsVo();
        returnGoodsVo.setSTime(dataVo.getSTime());
        returnGoodsVo.setETime(dataVo.getETime());
        returnGoodsVo.setState(1);
        return this.returnListDao.list(returnGoodsVo);
    }

    @Override
    public void saveReturnList(ReturnList returnList) {
        this.returnListDao.saveReturnList(returnList);
    }

    @Override
    public List<ReturnList> list(ReturnGoodsVo returnGoodsVo) {
        return this.returnListDao.list(returnGoodsVo);
    }

    @Override
    public void deleteReturnList(Integer returnListId) {
        this.returnListDao.deleteReturnList(returnListId);
    }

    @Override
    public ReturnList getById(Integer returnListId) {
        return this.returnListDao.getById(returnListId);
    }
}