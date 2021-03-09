package com.atguigu.jxc.dao;

import com.atguigu.jxc.entity.PurchaseListGoods;
import com.atguigu.jxc.entity.ReturnList;
import com.atguigu.jxc.entity.ReturnListGoods;
import com.atguigu.jxc.vo.CountPurchaseVo;
import com.atguigu.jxc.vo.ReturnListVo;

import java.util.List;

public interface ReturnDao {
    void saveReturnList(ReturnList returnList);

    void saveReturnListGoods(ReturnListGoods returnListGoods);


    ReturnListVo[] listReturnList(String returnNumber, Integer supplierId, Integer state, String sTime, String eTime);

    List<PurchaseListGoods> goodlist(Integer returnListId);

    void deleteReturnList(Integer returnListId);

    void deleteReturnListGoods(Integer returnListId);

    List<CountPurchaseVo> countReturn(String sTime, String eTime, Integer goodsTypeId, String codeOrName);
}
