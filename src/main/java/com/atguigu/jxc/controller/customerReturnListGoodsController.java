package com.atguigu.jxc.controller;

import com.atguigu.jxc.service.CustomerReturnListGoodsService;
import com.atguigu.jxc.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/customerReturnListGoods")
public class customerReturnListGoodsController {
    @Autowired
    private CustomerReturnListGoodsService customerReturnListGoodsService;

    @PostMapping("/list")
    @ResponseBody
    public Map<String,Object> countCustomerList(String sTime, String eTime, Integer goodsTypeId, String codeOrName){
        return customerReturnListGoodsService.countCustomerList(sTime,eTime,goodsTypeId,codeOrName);
    }
}
