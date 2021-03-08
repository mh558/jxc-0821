package com.atguigu.jxc.dao;

import com.atguigu.jxc.entity.PurchaseList;
import com.atguigu.jxc.entity.PurchaseListGoods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PurchaseListGoodsDao {

    void save(@Param("purchaseListGoodsList") List<PurchaseListGoods> purchaseListGoodsList);

    List<PurchaseListGoods> goodsList(@Param("purchaseListId") Integer purchaseListId);

    Integer deletePurchaseList(Integer purchaseListId);
}