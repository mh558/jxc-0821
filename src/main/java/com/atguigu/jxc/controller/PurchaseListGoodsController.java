package com.atguigu.jxc.controller;

import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.domain.SuccessCode;
import com.atguigu.jxc.entity.PurchaseList;
import com.atguigu.jxc.entity.PurchaseListGoods;
import com.atguigu.jxc.service.PurchaseListGoodsService;
import com.atguigu.jxc.vo.PurListVo;
import com.atguigu.jxc.vo.PurchaseGoodsVo;
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



    @PostMapping("count")
    public String count(PurListVo purListVo){

      String json=  this.purchaseListGoodsService.count(purListVo);
      return json;
    }
    /**
     *
     * @param purchaseListId 进货单Id
     * @return
     */
    @PostMapping("updateState")
    public ServiceVO updateState(@RequestParam("purchaseListId") Integer purchaseListId){
        this.purchaseListGoodsService.updateState(purchaseListId);
        return new ServiceVO(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);
    }
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
     * @param goodsVo
     * @return
     */
    @PostMapping("list")
    public Map<String,Object> list(PurchaseGoodsVo goodsVo){
        Map<String,Object> map=new HashMap<>();

        List<PurchaseList> lists=this.purchaseListGoodsService.list(goodsVo);
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