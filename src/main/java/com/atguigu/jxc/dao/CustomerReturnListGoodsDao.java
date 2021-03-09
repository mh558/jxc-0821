package com.atguigu.jxc.dao;

import com.atguigu.jxc.domain.QueryParam;
import com.atguigu.jxc.domain.ReturnQueryParam;
import com.atguigu.jxc.entity.CustomerReturnList;
import com.atguigu.jxc.entity.CustomerReturnListGoods;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CustomerReturnListGoodsDao {

    void saveCustomerReturnList(CustomerReturnList customerReturnList);

    void saveCustomerReturnListGoods(@Param("listGoods") List<CustomerReturnListGoods> listGoods);

    void deleteList(Integer returnListId);

    void deleteListGoods(Integer returnListId);

    List<CustomerReturnList> queryReturnList(ReturnQueryParam queryParam);

    List<CustomerReturnListGoods> queryListGoods(Integer returnListId);

}
