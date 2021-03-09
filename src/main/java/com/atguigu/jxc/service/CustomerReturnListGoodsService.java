package com.atguigu.jxc.service;

import java.util.Map;

public interface CustomerReturnListGoodsService {
    Map<String,Object> countCustomerList(String sTime, String eTime, Integer goodsTypeId, String codeOrName);
}
