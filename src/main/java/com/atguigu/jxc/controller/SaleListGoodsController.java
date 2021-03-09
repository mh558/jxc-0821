package com.atguigu.jxc.controller;

import com.atguigu.jxc.service.SaleListGoodsService;
import com.atguigu.jxc.vo.DateParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("saleListGoods")
public class SaleListGoodsController {

    @Autowired
    private SaleListGoodsService saleListGoodsService;

    @PostMapping("getSaleDataByDay")
    public String getSaleDataByDay(DateParam dataVo){
       String json= this.saleListGoodsService.getSaleDataByDay(dataVo);
       return json;
    }
}