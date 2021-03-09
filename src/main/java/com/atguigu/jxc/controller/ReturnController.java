package com.atguigu.jxc.controller;

import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.entity.ReturnList;
import com.atguigu.jxc.service.ReturnService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/returnListGoods")
public class ReturnController {

    @Autowired
    private ReturnService returnService;


    @PostMapping("/save")
    @ResponseBody
    @RequiresPermissions(value = "退货出库")
    public ServiceVO saveReturnList(ReturnList returnList, String returnListGoodsStr, HttpSession session){
        return returnService.saveReturnList(returnList,returnListGoodsStr,session);
    }

    @PostMapping("/list")
    @ResponseBody
    public Map<String,Object> list(String returnNumber, Integer supplierId, Integer state, String sTime, String eTime, HttpSession session){
        return returnService.listReturnList(returnNumber,supplierId,state,sTime,eTime,session);

    }

    @PostMapping("/goodsList")
    @ResponseBody
    public Map<String,Object> goodlist(Integer returnListId){
        return returnService.goodlist(returnListId);
    }

    @PostMapping("/delete")
    @ResponseBody
    public ServiceVO deleteReturnList(Integer returnListId){
        return returnService.deleteReturnList(returnListId);
    }

    @PostMapping("/count")
    @ResponseBody
    public String countReturn(String sTime, String eTime ,Integer goodsTypeId, String codeOrName){
        return returnService.countReturn(sTime,eTime,goodsTypeId,codeOrName);

    }

}
