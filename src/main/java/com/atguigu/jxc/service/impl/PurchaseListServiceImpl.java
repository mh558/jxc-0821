package com.atguigu.jxc.service.impl;

import com.atguigu.jxc.dao.PurchaseListDao;
import com.atguigu.jxc.entity.PurchaseList;
import com.atguigu.jxc.service.PurchaseListService;
import com.atguigu.jxc.vo.DateParam;
import com.atguigu.jxc.vo.PurchaseGoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseListServiceImpl implements PurchaseListService {

    @Autowired
    private PurchaseListDao purchaseListDao;

    @Override
    public List<PurchaseList> getSaleDataByDay(DateParam dataVo) {
        PurchaseGoodsVo purchaseGoodsVo = new PurchaseGoodsVo();
        purchaseGoodsVo.setETime(dataVo.getETime());
        purchaseGoodsVo.setSTime(dataVo.getSTime());
        purchaseGoodsVo.setState(1);
        return this.purchaseListDao.list(purchaseGoodsVo);
    }

    @Override
    public void save(PurchaseList purchaseList) {
        this.purchaseListDao.save(purchaseList);
    }

    @Override
    public List<PurchaseList> list(PurchaseGoodsVo goodsVo) {
        return this.purchaseListDao.list(goodsVo);
    }

    @Override
    public void deletePurchaseList(Integer purchaseListId) {
        this.purchaseListDao.deletePurchaseList(purchaseListId);
    }

    @Override
    public void updateState(PurchaseList purchaseList) {
        this.updateState(purchaseList);
    }

    @Override
    public PurchaseList getById(Integer purchaseListId) {
        return this.purchaseListDao.getById(purchaseListId);
    }
}