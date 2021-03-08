package com.atguigu.jxc.service.impl;

import com.atguigu.jxc.dao.PurchaseListDao;
import com.atguigu.jxc.dao.PurchaseListGoodsDao;
import com.atguigu.jxc.entity.Log;
import com.atguigu.jxc.entity.PurchaseList;
import com.atguigu.jxc.entity.PurchaseListGoods;
import com.atguigu.jxc.entity.User;
import com.atguigu.jxc.service.LogService;
import com.atguigu.jxc.service.PurchaseListGoodsService;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import sun.dc.pr.PRError;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PurchaseListGoodsServiceImpl implements PurchaseListGoodsService {

    @Autowired
    private PurchaseListGoodsDao purchaseListGoodsDao;
    @Autowired
    private LogService logService;
    @Autowired
    private PurchaseListDao purchaseListDao;

    private final static Gson GSON=new Gson();

    @Override
    @Transactional
    public void save(PurchaseList purchaseList, String purchaseNumber, String purchaseListGoodsStr, HttpSession session) {


        User user = (User) session.getAttribute("currentUser");
        purchaseList.setPurchaseNumber(purchaseNumber);
        purchaseList.setUserId(user.getUserId());
        this.purchaseListDao.save(purchaseList);


      //添加商品列表
        if(StringUtils.isEmpty(purchaseListGoodsStr)){
            List<PurchaseListGoods> purchaseListGoodsList = GSON.fromJson(purchaseListGoodsStr, new TypeToken<List<PurchaseListGoods>>()                      {
            }.getType());
          if(!CollectionUtils.isEmpty(purchaseListGoodsList)){
              this.purchaseListGoodsDao.save( purchaseListGoodsList.stream().map(t -> {
                  t.setPurchaseListId(purchaseList.getPurchaseListId());
                  return t;
              }).collect(Collectors.toList()));
          }
            this.logService.save(new Log(Log.INSERT_ACTION,"进货单保存"));
        }

    }

    @Override
    public List<PurchaseList> list(String purchaseNumber, Integer supplierId, Integer state, String sTime, String eTime) {
        this.logService.save(new Log(Log.SELECT_ACTION,"进货单查询"));
     return    this.purchaseListDao.list(purchaseNumber,supplierId,state,sTime,eTime);

    }

    @Override
    public List<PurchaseListGoods> goodsList(Integer purchaseListId) {
        if(purchaseListId==null){
            return null;
        }
        this.logService.save(new Log(Log.SELECT_ACTION,"进货单商品列表查询"));
       return this.purchaseListGoodsDao.goodsList(purchaseListId);

    }

    @Override
    @Transactional
    public void deletePurchaseList(Integer purchaseListId) {
        if(purchaseListId==null) {
            return;
        }
        Integer flag = this.purchaseListGoodsDao.deletePurchaseList(purchaseListId);
        if(flag>0){
            this.purchaseListDao.deletePurchaseList(purchaseListId);
        }
        this.logService.save(new Log(Log.DELETE_ACTION,"进货单删除"));


    }
}