package com.atguigu.jxc.controller;

import com.atguigu.jxc.domain.*;
import com.atguigu.jxc.entity.SaleList;
import com.atguigu.jxc.entity.SaleListGoods;
import com.atguigu.jxc.service.SaleListGoodsService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/saleListGoods")
public class SaleListGoodsController {

    @Autowired
    private SaleListGoodsService saleListGoodsService;

    @PostMapping("/count")
    public String countSaleListGoods(CountQueryParam countQueryParam){

        List<CountSaleGoodsVo> list = saleListGoodsService.countSaleListGoods(countQueryParam);

        Gson gson = new Gson();
        String json = gson.toJson(list);
        System.out.println(json);

        return json;

    }


    @PostMapping("/updateState")
    public ServiceVO updateState(Integer saleListId){

        saleListGoodsService.updateState(saleListId);

        return new ServiceVO(SuccessCode.SUCCESS_CODE,SuccessCode.SUCCESS_MESS);
    }

    @PostMapping("/delete")
    public ServiceVO deleteSaleListGoods(Integer saleListId){

        saleListGoodsService.deleteSaleListGoods(saleListId);

        return new ServiceVO(SuccessCode.SUCCESS_CODE,SuccessCode.SUCCESS_MESS);

    }

    @PostMapping("/goodsList")
    public Map<String,Object> querySaleListGoods(Integer saleListId){

        List<SaleListGoods> list = saleListGoodsService.querySaleListGoods(saleListId);

        Map map = new HashMap<>();
        map.put("rows",list);

        return map;

    }

    @PostMapping("/list")
    public Map<String , Object> querySaleList(QueryParam queryParam){

        Map map =new HashMap();

       List<SaleListGoodsVo> list = saleListGoodsService.querSaleyList(queryParam);

       map.put("rows",list);
        return map;
    }

    @PostMapping("/save")
    public ServiceVO saveSaleListGoods(@RequestParam("saleNumber")String saleNumber, SaleList saleList, String saleListGoodsStr){

        saleListGoodsService.saveSaleListGoods(saleNumber,saleList,saleListGoodsStr);

        return new ServiceVO(SuccessCode.SUCCESS_CODE,SuccessCode.SUCCESS_MESS);
    }



}
