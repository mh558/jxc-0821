package com.atguigu.jxc.dao;

import com.atguigu.jxc.entity.PurchaseList;
import com.atguigu.jxc.entity.PurchaseListGoods;
import com.atguigu.jxc.vo.CountPurchaseVo;
import com.atguigu.jxc.vo.PurchaseListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PurChaseDao {
    void savePurchaseList(PurchaseList purchaseList);

    void savePurchaseListGoods(PurchaseListGoods purchaseListGoods);

    PurchaseListVo[] listPurchaseList(String purchaseNumber, Integer supplierId, Integer state, String sTime, String eTime);

    List<PurchaseListGoods> goodlist(Integer purchaseListId);

    void deletePurchaseList(Integer purchaseListId);

    void deletePurchaseListGoods(Integer purchaseListId);

    void updateState(Integer purchaseListId);

    List<CountPurchaseVo> countPurchase(String sTime, String eTime, Integer goodsTypeId, @Param("codeOrName")String codeOrName);
}
