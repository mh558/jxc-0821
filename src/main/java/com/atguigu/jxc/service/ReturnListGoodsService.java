package com.atguigu.jxc.service;

import com.atguigu.jxc.entity.ReturnList;
import com.atguigu.jxc.entity.ReturnListGoods;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface ReturnListGoodsService {
    void saveReturnListGoods(String returnNumber, ReturnList returnList, String returnListGoodsStr, HttpSession session);

    List<ReturnList> list(String purchaseNumber, Integer supplierId, Integer state, String sTime, String eTime);

    List<ReturnListGoods> goodsList(Integer returnListId);

    void deleteReturnList(Integer returnListId);
}
