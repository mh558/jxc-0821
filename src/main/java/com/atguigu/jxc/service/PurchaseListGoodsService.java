package com.atguigu.jxc.service;

import com.atguigu.jxc.entity.PurchaseList;
import com.atguigu.jxc.entity.PurchaseListGoods;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface PurchaseListGoodsService {
    void save(PurchaseList purchaseList, String purchaseNumber, String purchaseListGoodsStr, HttpSession session);

    List<PurchaseList> list(String purchaseNumber, Integer supplierId, Integer state, String sTime, String eTime);

    List<PurchaseListGoods> goodsList(Integer purchaseListId);

    void deletePurchaseList(Integer purchaseListId);
}