package com.atguigu.jxc.service.impl;

import com.atguigu.jxc.dao.CustomerDao;
import com.atguigu.jxc.dao.GoodsDao;
import com.atguigu.jxc.dao.SaleListGoodsDao;
import com.atguigu.jxc.dao.UserDao;
import com.atguigu.jxc.domain.CountQueryParam;
import com.atguigu.jxc.domain.CountSaleGoodsVo;
import com.atguigu.jxc.domain.QueryParam;
import com.atguigu.jxc.domain.SaleListGoodsVo;
import com.atguigu.jxc.entity.*;
import com.atguigu.jxc.exception.SaleListGoodsException;
import com.atguigu.jxc.service.SaleListGoodsService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SaleListGoodsServiceImpl implements SaleListGoodsService {

    @Autowired
    private SaleListGoodsDao saleListGoodsDao;

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CustomerDao customerDao;

    @Override
    @Transactional
    public void saveSaleListGoods(String saleNumber, SaleList saleList, String saleListGoodsStr) {

        saleList.setSaleNumber(saleNumber);
        saleList.setUserId(1);
        saleListGoodsDao.saveSaleList(saleList);

        Integer saleListId = saleList.getSaleListId();

        Gson gson = new Gson();
        //List list = gson.fromJson(saleListGoodsStr, List.class);

        List<SaleListGoods> saleListGoodsList = gson.fromJson(saleListGoodsStr, new TypeToken<List<SaleListGoods>>() {
        }.getType());

        saleListGoodsList.forEach(saleListGoods -> {
            //获取goodsId，查询库存
            Integer goodsId = saleListGoods.getGoodsId();
            Goods goods = goodsDao.findByGoodsId(goodsId);
            Integer inventoryQuantity = goods.getInventoryQuantity();
            Integer goodsNum = saleListGoods.getGoodsNum();

            if (inventoryQuantity<goodsNum){
               throw new SaleListGoodsException("库存不足");
            }

            goods.setInventoryQuantity(inventoryQuantity-goodsNum);
            goodsDao.updateGoods(goods);

            saleListGoods.setSaleListId(saleListId);
            saleListGoodsDao.saveSaleListGoods(saleListGoods);


        });


    }

    @Override
    public List<SaleListGoodsVo> querSaleyList(QueryParam queryParam) {

        List<SaleList> list = saleListGoodsDao.querSaleyList(queryParam);

        ArrayList<SaleListGoodsVo> saleListGoodsVos = new ArrayList<>();

        list.forEach(saleList -> {

            SaleListGoodsVo saleListGoodsVo = new SaleListGoodsVo();
            BeanUtils.copyProperties(saleList,saleListGoodsVo);

            Customer customer = customerDao.getCustomerById(saleList.getCustomerId());
            saleListGoodsVo.setCustomerName(customer.getCustomerName());

            User user = userDao.getUserById(saleList.getUserId());
            saleListGoodsVo.setTrueName(user.getTrueName());
            saleListGoodsVos.add(saleListGoodsVo);

        });

        return saleListGoodsVos;
    }

    @Override
    public List<SaleListGoods> querySaleListGoods(Integer saleListId) {

        List<SaleListGoods> list = saleListGoodsDao.querySaleListGoods(saleListId);

        return list;
    }

    @Override
    @Transactional
    public void deleteSaleListGoods(Integer saleListId) {

        saleListGoodsDao.deleteSaleListGoods(saleListId);

        saleListGoodsDao.deleteSaleList(saleListId);

    }

    @Override
    public void updateState(Integer saleListId) {
        saleListGoodsDao.updateState(saleListId);
    }

    @Override
    public List<CountSaleGoodsVo> countSaleListGoods(CountQueryParam countQueryParam) {

        List<CountSaleGoodsVo> list = saleListGoodsDao.countSaleListGoods(countQueryParam);
        return list;


    }


}
