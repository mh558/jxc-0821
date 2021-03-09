package com.atguigu.jxc.service;

import com.atguigu.jxc.entity.PurchaseList;
import com.atguigu.jxc.entity.PurchaseListGoods;
import com.atguigu.jxc.vo.PurListVo;
import com.atguigu.jxc.vo.PurchaseGoodsVo;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface PurchaseListGoodsService {
    void save(PurchaseList purchaseList, String purchaseNumber, String purchaseListGoodsStr, HttpSession session);

    List<PurchaseList> list(PurchaseGoodsVo goodsVo);

    List<PurchaseListGoods> goodsList(Integer purchaseListId);

    void deletePurchaseList(Integer purchaseListId);

    void updateState(Integer purchaseListId);

    String count(PurListVo purListVo);

    Integer listByDay(List<Integer> psId);

    List<PurchaseListGoods> queryListByPsId(List<Integer> psId);
}