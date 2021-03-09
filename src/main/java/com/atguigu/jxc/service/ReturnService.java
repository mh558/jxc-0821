package com.atguigu.jxc.service;

import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.entity.ReturnList;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface ReturnService {
    ServiceVO saveReturnList(ReturnList returnList, String returnListGoodsStr, HttpSession session);

    Map<String, Object> listReturnList(String returnNumber, Integer supplierId, Integer state, String sTime, String eTime, HttpSession session);

    Map<String, Object> goodlist(Integer returnListId);

    ServiceVO deleteReturnList(Integer returnListId);

    String countReturn(String sTime, String eTime, Integer goodsTypeId, String codeOrName);
}
