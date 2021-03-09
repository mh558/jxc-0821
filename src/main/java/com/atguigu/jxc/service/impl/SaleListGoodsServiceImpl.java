package com.atguigu.jxc.service.impl;

import com.atguigu.jxc.dao.SaleListGoodsDao;
import com.atguigu.jxc.entity.*;
import com.atguigu.jxc.service.*;
import com.atguigu.jxc.vo.DateParam;
import com.atguigu.jxc.vo.SaleListVo;
import com.google.gson.Gson;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.sound.midi.Soundbank;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleListGoodsServiceImpl implements SaleListGoodsService {

    @Autowired
    private SaleListGoodsDao saleListGoodsDao;
    @Autowired
    private SaleListService saleListService;
    @Autowired
    private PurchaseListGoodsService purchaseListGoodsService;
    @Autowired
    private PurchaseListService purchaseListService;
    @Autowired
    private ReturnListGoodsService returnListGoodsService;
    @Autowired
    private ReturnListService returnListService;

    private final static Gson GSON=new Gson();


    @Override
    public String getSaleDataByDay(DateParam dataVo) {
        SaleListVo saleListVo = new SaleListVo();

        Integer profit=null;
        //获取销售总数量
        List<SaleList> saleLists=  this.saleListService.getSaleDataByDay(dataVo);
        if(!CollectionUtils.isEmpty(saleLists)){
            List<Integer> collect = saleLists.stream().map(SaleList :: getSaleListId).collect(Collectors.toList());
            Integer saleDataByDay = this.saleListGoodsDao.getSaleDataByDay(collect);
            saleListVo.setSaleTotal(saleDataByDay);
            List<SaleListGoods> saleListGoods = this.saleListGoodsDao.list(collect);
            if(!CollectionUtils.isEmpty(saleListGoods)){
                List<Double> total = saleListGoods.stream().map(SaleListGoods :: getTotal).collect(Collectors.toList());
                Integer intValue = total.stream().reduce((a, b) -> a + b).get().intValue();
                System.out.println(intValue);
                if(intValue!=null){
                    profit=intValue;
                }
            }
        }

        //获取进货总量
        List<PurchaseList> purchaseLists= this.purchaseListService.getSaleDataByDay(dataVo);
        if(!CollectionUtils.isEmpty(purchaseLists)){
            List<Integer> psId = purchaseLists.stream().map(PurchaseList :: getPurchaseListId).collect(Collectors.toList());
            Integer count=  this.purchaseListGoodsService.listByDay(psId);
            saleListVo.setPurchasingTotal(count);
         List<PurchaseListGoods> purchaseListGoods= this.purchaseListGoodsService.queryListByPsId(psId);
         if(!CollectionUtils.isEmpty(purchaseListGoods)){
             List<Double> total = purchaseListGoods.stream().map(PurchaseListGoods :: getTotal).collect(Collectors.toList());
             if(!CollectionUtils.isEmpty(total)){
                 Integer value = total.stream().reduce((a, b) -> a + b).get().intValue();
                 System.out.println(value);
                 if(value!=null){
                     profit=profit-value;
                 }
             }
         }
        }

        //获取总盈利
        List<ReturnList> returnLists= this.returnListService.getSaleDataByDay(dataVo);
        if(!CollectionUtils.isEmpty(returnLists)){
            List<Integer> collect = returnLists.stream().map(ReturnList :: getReturnListId).collect(Collectors.toList());
            List<ReturnListGoods> returnListGoods= this.returnListGoodsService.listByDay(collect);
            if(!CollectionUtils.isEmpty(returnListGoods)){
                List<Double> total = returnListGoods.stream().map(ReturnListGoods :: getTotal).collect(Collectors.toList());
                if(!CollectionUtils.isEmpty(total)){
                    Integer value = total.stream().reduce((a, b) -> a + b).get().intValue();
                    System.out.println(value);
                    if(value!=null) {
                       profit=profit-value;
                    }
                }
            }
        }
        saleListVo.setProfit(profit);
        return GSON.toJson(saleListVo);

    }
}