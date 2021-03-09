package com.atguigu.jxc.service.impl;

import com.atguigu.jxc.dao.GoodsDao;
import com.atguigu.jxc.dao.PurChaseDao;
import com.atguigu.jxc.dao.SupplierDao;
import com.atguigu.jxc.dao.UserDao;
import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.domain.SuccessCode;
import com.atguigu.jxc.entity.*;
import com.atguigu.jxc.service.PurChaseService;
import com.atguigu.jxc.util.GsonUtil;
import com.atguigu.jxc.vo.CountPurchaseVo;
import com.atguigu.jxc.vo.PurchaseListVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.omg.PortableInterceptor.SUCCESSFUL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@Service
public class PurChaseServiceImpl implements PurChaseService {
    @Autowired
    private PurChaseDao purChaseDao;
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private UserServiceImpl userServiceimpl;
    @Autowired
    private SupplierDao supplierDao;

    @Override
    public ServiceVO savePurChaseList(String purchaseListGoodsStr, PurchaseList purchaseList, HttpSession session) {
        //新增t_purchase_list表
        String purchaseNumber = purchaseList.getPurchaseNumber();
        //获取当前用户id
        User user = (User) session.getAttribute("currentUser");
        purchaseList.setUserId(user.getUserId());
        purChaseDao.savePurchaseList(purchaseList);
        //获取goods集合
        Integer purchaseListId = purchaseList.getPurchaseListId();
        List<PurchaseListGoods> purchaseListGoodsList = new ArrayList<>();
        Gson gson = new Gson();
        purchaseListGoodsList  = gson.fromJson(purchaseListGoodsStr,new TypeToken<List<PurchaseListGoods>>(){}.getType());
//        purchaseListGoodsList  = GsonUtil.getPersons(purchaseListGoodsStr, PurchaseListGoods.class);
//        double amountPaid = purchaseListGoodsList.stream().mapToDouble(PurchaseListGoods::getPrice).sum();
        for (PurchaseListGoods purchaseListGoods : purchaseListGoodsList) {
            purchaseListGoods.setPurchaseListId(purchaseListId);
            purChaseDao.savePurchaseListGoods(purchaseListGoods);
            //更新t_goods表库存量
            Goods byGoodsId = goodsDao.findByGoodsId(purchaseListGoods.getGoodsId());
            byGoodsId.setInventoryQuantity(byGoodsId.getInventoryQuantity()+purchaseListGoods.getGoodsNum());
            goodsDao.updateGoods(byGoodsId);
        }
        return new ServiceVO(SuccessCode.SUCCESS_CODE,SuccessCode.SUCCESS_MESS);
    }

    @Override
    public Map<String, Object> listPurchaseList(String purchaseNumber, Integer supplierId, Integer state, String sTime, String eTime, HttpSession session) {
        PurchaseListVo[] purchaseListVos= purChaseDao.listPurchaseList(purchaseNumber,supplierId,state,sTime,eTime);
        Map map = new HashMap();
        for (PurchaseListVo purchaseListVo : purchaseListVos) {
            Supplier supplierById = supplierDao.getSupplierById(purchaseListVo.getSupplierId());
            purchaseListVo.setSupplierName(supplierById.getSupplierName());
            User user = (User) session.getAttribute("currentUser");
            purchaseListVo.setTrueName(user.getTrueName());
        }
        map.put("rows",purchaseListVos);
        return map;
    }

    @Override
    public Map<String, Object> goodlist(Integer purchaseListId) {
        Map map = new HashMap();
        List<PurchaseListGoods> purchaseListGoodsList = purChaseDao.goodlist(purchaseListId);
        map.put("rows",purchaseListGoodsList);
        return map;
    }

    @Override
    public ServiceVO deletePurchaseList(Integer purchaseListId) {
        purChaseDao.deletePurchaseListGoods(purchaseListId);
        purChaseDao.deletePurchaseList(purchaseListId);
        return new ServiceVO(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);
    }

    @Override
    public ServiceVO updateState(Integer purchaseListId) {
        purChaseDao.updateState(purchaseListId);
        return new ServiceVO(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);
    }

    @Override
    public String countPurchase(String sTime, String eTime, Integer goodsTypeId, String codeOrName) {
        List<CountPurchaseVo> countPurchaseVos = new ArrayList<>();
        countPurchaseVos = purChaseDao.countPurchase(sTime,eTime,goodsTypeId,codeOrName);
        Gson gson = new Gson();
        String toJson = gson.toJson(countPurchaseVos);
        System.out.println(toJson);
        return toJson;
    }
}
