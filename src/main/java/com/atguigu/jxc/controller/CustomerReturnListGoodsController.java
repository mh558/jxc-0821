package com.atguigu.jxc.controller;

import com.atguigu.jxc.domain.*;
import com.atguigu.jxc.entity.CustomerReturnList;
import com.atguigu.jxc.entity.CustomerReturnListGoods;
import com.atguigu.jxc.entity.SaleListGoods;
import com.atguigu.jxc.service.CustomerReturnListGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customerReturnListGoods")
public class CustomerReturnListGoodsController {

    @Autowired
    private CustomerReturnListGoodsService customerReturnListGoodsService;


    @PostMapping("/delete")
    public ServiceVO deleteSaleListGoods(Integer customerReturnListId){

        customerReturnListGoodsService.deleteListGoods(customerReturnListId);

        return new ServiceVO(SuccessCode.SUCCESS_CODE,SuccessCode.SUCCESS_MESS);

    }

    @PostMapping("/goodsList")
    public Map<String,Object> querySaleListGoods(Integer customerReturnListId){

        List<CustomerReturnListGoods> list = customerReturnListGoodsService.queryListGoods(customerReturnListId);

        Map map = new HashMap<>();
        map.put("rows",list);

        return map;

    }

    @PostMapping("/list")
    public Map<String , Object> querySaleList(ReturnQueryParam queryParam){

        Map map =new HashMap();

        List<CustomerReturnListGoodsVo> list = customerReturnListGoodsService.querReturnList(queryParam);

        map.put("rows",list);
        return map;
    }


    @PostMapping("/save")
    public ServiceVO saveCustomerReturnListGoods(@RequestParam("returnNumber")String returnNumber,
                                                 CustomerReturnList customerReturnList,
                                                 String customerReturnListGoodsStr){


        customerReturnListGoodsService.saveCustomerReturnListGoods(returnNumber,customerReturnList,customerReturnListGoodsStr);

        return new ServiceVO(SuccessCode.SUCCESS_CODE,SuccessCode.SUCCESS_MESS);
    }

}
