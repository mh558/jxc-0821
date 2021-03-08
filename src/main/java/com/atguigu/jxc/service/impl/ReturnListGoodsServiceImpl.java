package com.atguigu.jxc.service.impl;

import com.atguigu.jxc.dao.ReturnListDao;
import com.atguigu.jxc.dao.ReturnListGoodsDao;
import com.atguigu.jxc.entity.Log;
import com.atguigu.jxc.entity.ReturnList;
import com.atguigu.jxc.entity.ReturnListGoods;
import com.atguigu.jxc.entity.User;
import com.atguigu.jxc.exception.UntifyException;
import com.atguigu.jxc.service.LogService;
import com.atguigu.jxc.service.ReturnListGoodsService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;


import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReturnListGoodsServiceImpl implements ReturnListGoodsService {

    @Autowired
    private ReturnListGoodsDao returnListGoodsDao;
    @Autowired
    private ReturnListDao returnListDao;
    @Autowired
    private LogService logService;

    private final static Gson GSON=new Gson();


    @Override
    @Transactional
    public void saveReturnListGoods(String returnNumber, ReturnList returnList, String returnListGoodsStr, HttpSession session) {

        User currentUser = (User) session.getAttribute("currentUser");
        if(currentUser==null || StringUtils.isEmpty(returnListGoodsStr) || StringUtils.isEmpty(returnNumber) ){
           throw new UntifyException("请进行登录");
        }
        returnList.setReturnNumber(returnNumber);
        returnList.setUserId(currentUser.getUserId());
        this.returnListDao.saveReturnList(returnList);

        List<ReturnListGoods> returnListGoods = GSON.fromJson(returnListGoodsStr, new TypeToken<List<ReturnListGoods>>() {
        }.getType());
        if(!CollectionUtils.isEmpty(returnListGoods)){
            this.returnListGoodsDao.saveReturnListGoods(returnListGoods.stream().map(t->{
                t.setReturnListId(returnList.getReturnListId());
                return t;
            }).collect(Collectors.toList()));
        }
        this.logService.save(new Log(Log.INSERT_ACTION,"退货单保存"));


    }

    @Override
    public List<ReturnList> list(String returnNumber, Integer supplierId, Integer state, String sTime, String eTime) {
      List<ReturnList> returnLists=  this.returnListDao.list(returnNumber,supplierId,state,sTime,eTime);
        this.logService.save(new Log(Log.SELECT_ACTION,"退货单查询"));
        return returnLists;
    }

    @Override
    public List<ReturnListGoods> goodsList(Integer returnListId) {
        if(StringUtils.isEmpty(returnListId)){
            return null;
        }
        this.logService.save(new Log(Log.SELECT_ACTION,"退货单具体商品查询"));
        return this.returnListGoodsDao.goodsList(returnListId);
    }

    @Override
    @Transactional
    public void deleteReturnList(Integer returnListId) {
        if(StringUtils.isEmpty(returnListId)){
            throw new UntifyException("请选择要删除的退货单");
        }
        Integer flag=  this.returnListGoodsDao.deleteReturnList(returnListId);
        if(flag>0){
            this.returnListDao.deleteReturnList(returnListId);
        }
        this.logService.save(new Log(Log.DELETE_ACTION,"退货单删除"));
    }
}