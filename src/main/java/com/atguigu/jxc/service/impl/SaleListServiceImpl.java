package com.atguigu.jxc.service.impl;

import com.atguigu.jxc.dao.SaleListDao;
import com.atguigu.jxc.entity.SaleList;
import com.atguigu.jxc.service.SaleListService;
import com.atguigu.jxc.vo.DateParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleListServiceImpl implements SaleListService {

   @Autowired
    private SaleListDao saleListDao;

    @Override
    public List<SaleList> getSaleDataByDay(DateParam dataVo) {
        return saleListDao.getSaleDataByDay(dataVo);
    }
}