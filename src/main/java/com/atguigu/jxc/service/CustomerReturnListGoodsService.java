package com.atguigu.jxc.service;

import com.atguigu.jxc.domain.CustomerReturnListGoodsVo;
import com.atguigu.jxc.domain.QueryParam;
import com.atguigu.jxc.domain.ReturnQueryParam;
import com.atguigu.jxc.entity.CustomerReturnList;
import com.atguigu.jxc.entity.CustomerReturnListGoods;
import com.atguigu.jxc.entity.SaleListGoods;

import java.util.List;

public interface CustomerReturnListGoodsService {

    void saveCustomerReturnListGoods(String returnNumber, CustomerReturnList customerReturnList, String customerReturnListGoodsStr);


    void deleteListGoods(Integer returnListId);

    List<CustomerReturnListGoods> queryListGoods(Integer returnListId);

    List<CustomerReturnListGoodsVo> querReturnList(ReturnQueryParam queryParam);

}
