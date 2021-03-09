package com.atguigu.jxc.dao;

import com.atguigu.jxc.vo.CountPurchaseVo;

import java.util.List;

public interface CustomerReturnListGoodsDao {
    List<CountPurchaseVo> countCustomerList(String sTime, String eTime, Integer goodsTypeId, String codeOrName);
}
