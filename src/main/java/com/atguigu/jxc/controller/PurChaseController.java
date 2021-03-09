package com.atguigu.jxc.controller;

import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.domain.SuccessCode;
import com.atguigu.jxc.entity.PurchaseList;
import com.atguigu.jxc.service.PurChaseService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.omg.PortableInterceptor.SUCCESSFUL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/purchaseListGoods")
public class PurChaseController {
    @Autowired
    private PurChaseService purChaseService;

    @PostMapping("/save")
    @ResponseBody
    @RequiresPermissions(value = "进货入库")
    public ServiceVO savePurChaseList(PurchaseList purchaseList, String purchaseListGoodsStr, HttpSession session){
//        System.out.println("purchaseListGoodsStr = " + purchaseListGoodsStr);
//        System.out.println("purchaseList = " + purchaseList);
       return purChaseService.savePurChaseList(purchaseListGoodsStr,purchaseList,session);
    }


    @PostMapping("/list")
    @ResponseBody
    @RequiresPermissions(value = "进货入库")
    public Map<String,Object> list(String purchaseNumber, Integer supplierId, Integer state, String sTime, String eTime,HttpSession session){
       return purChaseService.listPurchaseList(purchaseNumber,supplierId,state,sTime,eTime,session);

    }

    @PostMapping("/goodsList")
    @ResponseBody
    @RequiresPermissions(value = "进货单据查询")
    public Map<String,Object> goodlist(Integer purchaseListId){
        return purChaseService.goodlist(purchaseListId);
    }

    @PostMapping("/delete")
    @ResponseBody
    public ServiceVO deletePurchaseList(Integer purchaseListId){
        return purChaseService.deletePurchaseList(purchaseListId);
    }

    @PostMapping("/updateState")
    @ResponseBody
    public ServiceVO updateState(Integer purchaseListId){

        return purChaseService.updateState(purchaseListId);
    }


    @PostMapping("/count")
    @ResponseBody
    public String countPurchase(String sTime, String eTime ,Integer goodsTypeId, String codeOrName){
       return purChaseService.countPurchase(sTime,eTime,goodsTypeId,codeOrName);

    }
}
