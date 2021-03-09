package com.atguigu.jxc.controller;

import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.domain.SuccessCode;
import com.atguigu.jxc.entity.ReturnList;
import com.atguigu.jxc.entity.ReturnListGoods;
import com.atguigu.jxc.service.ReturnListGoodsService;
import com.atguigu.jxc.vo.PurListVo;
import com.atguigu.jxc.vo.ReturnGoodsVo;
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

    @PostMapping("count")
    public String count(PurListVo purListVo){
    String json= this.returnListGoodsService.count(purListVo);
     return json;
    }

    @PostMapping("save")
    public ServiceVO save(@RequestParam("returnNumber") String returnNumber, @RequestParam("returnListGoodsStr") String returnListGoodsStr, ReturnList returnList, HttpSession session){

        this.returnListGoodsService.saveReturnListGoods(returnNumber,returnList,returnListGoodsStr,session);
        return new ServiceVO(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);
    }

    @PostMapping("list")
    public Map<String,Object> list(ReturnGoodsVo returnGoodsVo){
        Map<String,Object> map = new HashMap<>();
        List<ReturnList> returnLists=this.returnListGoodsService.list(returnGoodsVo);
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