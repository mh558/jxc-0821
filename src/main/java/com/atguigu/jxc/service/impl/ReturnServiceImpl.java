package com.atguigu.jxc.service.impl;

import com.atguigu.jxc.dao.GoodsDao;
import com.atguigu.jxc.dao.ReturnDao;
import com.atguigu.jxc.dao.SupplierDao;
import com.atguigu.jxc.dao.UserDao;
import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.domain.SuccessCode;
import com.atguigu.jxc.entity.*;
import com.atguigu.jxc.service.ReturnService;
import com.atguigu.jxc.vo.CountPurchaseVo;
import com.atguigu.jxc.vo.PurchaseListVo;
import com.atguigu.jxc.vo.ReturnListVo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReturnServiceImpl implements ReturnService {
    @Autowired
    private ReturnDao returnDao;
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private SupplierDao supplierDao;
    @Override
    public ServiceVO saveReturnList(ReturnList returnList, String returnListGoodsStr, HttpSession session) {
        //获取当前用户id
        User user = (User) session.getAttribute("currentUser");
        returnList.setUserId(user.getUserId());
        returnDao.saveReturnList(returnList);
        //获取goods集合
        Integer returnListId = returnList.getReturnListId();
        List<ReturnListGoods> returnListGoodsList = new ArrayList<>();
        Gson gson = new Gson();
        returnListGoodsList = gson.fromJson(returnListGoodsStr,new TypeToken<List<ReturnListGoods>>(){}.getType());
        for (ReturnListGoods returnListGoods : returnListGoodsList) {
            returnListGoods.setReturnListId(returnListId);
            returnDao.saveReturnListGoods(returnListGoods);
            //更新t_goods表库存量
            Goods byGoodsId = goodsDao.findByGoodsId(returnListGoods.getGoodsId());
            byGoodsId.setInventoryQuantity(byGoodsId.getInventoryQuantity()-returnListGoods.getGoodsNum());
            goodsDao.updateGoods(byGoodsId);
        }
        return new ServiceVO(SuccessCode.SUCCESS_CODE,SuccessCode.SUCCESS_MESS);
    }

    @Override
    public Map<String, Object> listReturnList(String returnNumber, Integer supplierId, Integer state, String sTime, String eTime, HttpSession session) {
        ReturnListVo[] returnListVos= returnDao.listReturnList(returnNumber,supplierId,state,sTime,eTime);
        Map map = new HashMap();
        for (ReturnListVo returnListVo : returnListVos) {
            Supplier supplierById = supplierDao.getSupplierById(returnListVo.getSupplierId());
            returnListVo.setSupplierName(supplierById.getSupplierName());
            User user = (User) session.getAttribute("currentUser");
            returnListVo.setTrueName(user.getTrueName());
        }
        map.put("rows",returnListVos);
        return map;
    }

    @Override
    public Map<String, Object> goodlist(Integer returnListId) {
        Map map = new HashMap();
        List<PurchaseListGoods> returnListGoodsList = returnDao.goodlist(returnListId);
        map.put("rows",returnListGoodsList);
        return map;
    }

    @Override
    public ServiceVO deleteReturnList(Integer returnListId) {
        returnDao.deleteReturnListGoods(returnListId);
        returnDao.deleteReturnList(returnListId);
        return new ServiceVO(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);
    }

    @Override
    public String countReturn(String sTime, String eTime, Integer goodsTypeId, String codeOrName) {
        List<CountPurchaseVo> countPurchaseVos = new ArrayList<>();
        countPurchaseVos = returnDao.countReturn(sTime,eTime,goodsTypeId,codeOrName);
        Gson gson = new Gson();
        String toJson = gson.toJson(countPurchaseVos);
//        System.out.println(toJson);
        return toJson;
    }
}
