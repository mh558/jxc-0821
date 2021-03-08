package com.atguigu.jxc.controller;

import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.domain.SuccessCode;
import com.atguigu.jxc.entity.ReturnList;
import com.atguigu.jxc.entity.ReturnListGoods;
import com.atguigu.jxc.service.ReturnListGoodsService;
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
@RequestMapping("returnListGoods")
public class ReturnListGoodsController {

    @Autowired
    private ReturnListGoodsService returnListGoodsService;


    @PostMapping("save")
    public ServiceVO save(@RequestParam("returnNumber") String returnNumber, @RequestParam("returnListGoodsStr") String returnListGoodsStr, ReturnList returnList, HttpSession session){

        this.returnListGoodsService.saveReturnListGoods(returnNumber,returnList,returnListGoodsStr,session);
        return new ServiceVO(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);
    }

    @PostMapping("list")
    public Map<String,Object> list(@RequestParam("returnNumber") String returnNumber,
                                   @RequestParam("supplierId") Integer supplierId,
                                   @RequestParam("state") Integer state,
                                   @RequestParam("sTime") String sTime,
                                   @RequestParam("eTime") String eTime){
        Map<String,Object> map = new HashMap<>();
        List<ReturnList> returnLists=this.returnListGoodsService.list(returnNumber,supplierId,state,sTime,eTime);
        map.put("rows", returnLists);

        return map;
    }

    @PostMapping("goodsList")
    public Map<String,Object> goodsList(@RequestParam("returnListId") Integer returnListId){
        Map<String,Object> map = new HashMap<>();
       List<ReturnListGoods> returnListGoods= this.returnListGoodsService.goodsList(returnListId);
       map.put("rows", returnListGoods);
        return map;
    }

    @PostMapping("delete")
    public ServiceVO deleteReturnList(@RequestParam("returnListId") Integer returnListId){
        this.returnListGoodsService.deleteReturnList(returnListId);
        return new ServiceVO(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);
    }
}