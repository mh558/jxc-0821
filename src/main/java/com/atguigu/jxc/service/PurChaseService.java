package com.atguigu.jxc.service;

import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.entity.PurchaseList;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface PurChaseService {
    ServiceVO savePurChaseList(String purchaseListGoodsStr, PurchaseList purchaseList, HttpSession session);

    Map<String, Object> listPurchaseList(String purchaseNumber, Integer supplierId, Integer state, String sTime, String eTime, HttpSession session);

    Map<String, Object> goodlist(Integer purchaseListId);

    ServiceVO deletePurchaseList(Integer purchaseListId);

    ServiceVO updateState(Integer purchaseListId);

    String countPurchase(String sTime, String eTime, Integer goodsTypeId, String codeOrName);
}
