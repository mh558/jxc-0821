package com.atguigu.jxc.service.impl;

import com.atguigu.jxc.dao.CustomerReturnListGoodsDao;
import com.atguigu.jxc.service.CustomerReturnListGoodsService;
import com.atguigu.jxc.vo.CountPurchaseVo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerReturnListGoodsServiceImpl implements CustomerReturnListGoodsService {
    @Autowired
    private CustomerReturnListGoodsDao customerReturnListGoodsDao;
    @Override
    public Map<String,Object> countCustomerList(String sTime, String eTime, Integer goodsTypeId, String codeOrName) {
        List<CountPurchaseVo> countPurchaseVos = new ArrayList<>();
        countPurchaseVos = customerReturnListGoodsDao.countCustomerList(sTime,eTime,goodsTypeId,codeOrName);
//        Gson gson = new Gson();
//        String toJson = gson.toJson(countPurchaseVos);
//        System.out.println(toJson);
//        return toJson;
        Map<String,Object> map = new HashMap<>();
        map.put("rows",countPurchaseVos);
        System.out.println("map = " + map);
        return map;
    }
}
