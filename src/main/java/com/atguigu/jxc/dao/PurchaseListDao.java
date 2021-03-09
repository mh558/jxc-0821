package com.atguigu.jxc.dao;


import com.atguigu.jxc.entity.PurchaseList;
import com.atguigu.jxc.vo.PurchaseGoodsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PurchaseListDao {
    Integer save(PurchaseList purchaseList);

    List<PurchaseList> list(PurchaseGoodsVo goodsVo);

    Integer deletePurchaseList(Integer purchaseListId);

    void updateState( PurchaseList purchaseList);

    PurchaseList getById(@Param("purchaseListId") Integer purchaseListId);

}
