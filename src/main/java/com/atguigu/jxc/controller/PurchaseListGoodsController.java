package com.atguigu.jxc.controller;

import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.domain.SuccessCode;
import com.atguigu.jxc.entity.PurchaseList;
import com.atguigu.jxc.entity.PurchaseListGoods;
import com.atguigu.jxc.entity.User;
import com.atguigu.jxc.service.PurchaseListGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("purchaseListGoods")
public class PurchaseListGoodsController {

    @Autowired
    private PurchaseListGoodsService purchaseListGoodsService;


    /**
     *
     * @param purchaseNumber 进货单号
     * @param purchaseList 进货单实体信息
     * @param purchaseListGoodsStr 进货单商品列表json信息
     * @param session
     * @return
     */
    @PostMapping("save")
    public ServiceVO save(@RequestParam("purchaseNumber") String purchaseNumber, PurchaseList purchaseList, @RequestParam("purchaseListGoodsStr") String purchaseListGoodsStr, HttpSession session){

        this.purchaseListGoodsService.save(purchaseList,purchaseNumber,purchaseListGoodsStr,session);
        return new ServiceVO(SuccessCode.SUCCESS_CODE,SuccessCode.SUCCESS_MESS);
    }

    /**
     *
     * @param purchaseNumber 进货单号
     * @param supplierId 供应商Id
     * @param state  支付状态
     * @param sTime   单据创建时间区间 开始日期
     * @param eTime    单据创建时间区间 结束日期
     * @return
     */
    @PostMapping("list")
    public Map<String,Object> list(@RequestParam("purchaseNumber") String purchaseNumber,
                                   @RequestParam("supplierId") Integer supplierId,
                                   @RequestParam("state") Integer state,
                                   @RequestParam("sTime") String sTime,
                                   @RequestParam("eTime") String eTime
                                   ){
        Map<String,Object> map=new HashMap<>();

        List<PurchaseList> lists=this.purchaseListGoodsService.list(purchaseNumber,supplierId,state,sTime,eTime);
        map.put("rows", lists);
        return map;
    }

    @PostMapping("delete")
    public ServiceVO deletePurchaseList(@RequestParam("purchaseListId") Integer purchaseListId){
        this.purchaseListGoodsService.deletePurchaseList(purchaseListId);
        return new ServiceVO(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);
    }

    /**
     *
     * @param purchaseListId 进货单id
     * @return  查询进货单商品信息
     */
    @PostMapping("goodsList")
    public Map<String,Object> goodsList(@RequestParam("purchaseListId") Integer purchaseListId){

        Map<String,Object> map=new HashMap<>();
        List<PurchaseListGoods> purchaseListGoods=  this.purchaseListGoodsService.goodsList(purchaseListId);
        map.put("rows", purchaseListGoods);
        return map;
    }
}