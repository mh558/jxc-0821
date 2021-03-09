package com.atguigu.jxc.service;

import com.atguigu.jxc.entity.PurchaseList;
import com.atguigu.jxc.vo.DateParam;
import com.atguigu.jxc.vo.PurchaseGoodsVo;

import java.util.List;

public interface PurchaseListService {

    List<PurchaseList> getSaleDataByDay(DateParam dataVo);

    void save(PurchaseList purchaseList);

    List<PurchaseList> list(PurchaseGoodsVo goodsVo);

    void deletePurchaseList(Integer purchaseListId);

    void updateState(PurchaseList purchaseList);

    PurchaseList getById(Integer purchaseListId);
}