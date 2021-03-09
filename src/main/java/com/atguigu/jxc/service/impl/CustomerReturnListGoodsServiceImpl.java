package com.atguigu.jxc.service.impl;

import com.atguigu.jxc.dao.CustomerDao;
import com.atguigu.jxc.dao.CustomerReturnListGoodsDao;
import com.atguigu.jxc.dao.GoodsDao;
import com.atguigu.jxc.dao.UserDao;
import com.atguigu.jxc.domain.*;
import com.atguigu.jxc.entity.*;
import com.atguigu.jxc.service.CustomerReturnListGoodsService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerReturnListGoodsServiceImpl implements CustomerReturnListGoodsService {

    @Autowired
    private CustomerReturnListGoodsDao customerReturnListGoodsDao;

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private UserDao userDao;

    @Override
    public void saveCustomerReturnListGoods(String returnNumber, CustomerReturnList customerReturnList, String customerReturnListGoodsStr) {

        customerReturnList.setReturnNumber(returnNumber);
        customerReturnList.setUserId(1);
        customerReturnListGoodsDao.saveCustomerReturnList(customerReturnList);

        Integer customerReturnListId = customerReturnList.getCustomerReturnListId();

        Gson gson = new Gson();
        List<CustomerReturnListGoods> list = gson.fromJson(customerReturnListGoodsStr, new TypeToken<List<CustomerReturnListGoods>>() {
        }.getType());

        List<CustomerReturnListGoods> listGoods = list.stream().map(customerReturnListGoods -> {
            customerReturnListGoods.setCustomerReturnListId(customerReturnListId);
            return customerReturnListGoods;
        }).collect(Collectors.toList());

        customerReturnListGoodsDao.saveCustomerReturnListGoods(listGoods);

        //增加相应库存
        listGoods.forEach(customerReturnListGoods -> {
            Integer goodsId = customerReturnListGoods.getGoodsId();
            Integer goodsNum = customerReturnListGoods.getGoodsNum();
            Goods goods = goodsDao.findByGoodsId(goodsId);
            goods.setInventoryQuantity(goods.getInventoryQuantity()+goodsNum);
            goodsDao.updateGoods(goods);
        });

    }

    @Override
    public void deleteListGoods(Integer returnListId) {

        customerReturnListGoodsDao.deleteListGoods(returnListId);

        customerReturnListGoodsDao.deleteList(returnListId);

    }

    @Override
    public List<CustomerReturnListGoods> queryListGoods(Integer returnListId) {

        List<CustomerReturnListGoods> list = customerReturnListGoodsDao.queryListGoods(returnListId);

        return list;
    }


    @Override
    public List<CustomerReturnListGoodsVo> querReturnList(ReturnQueryParam queryParam) {
        List<CustomerReturnList> list = customerReturnListGoodsDao.queryReturnList(queryParam);

        ArrayList<CustomerReturnListGoodsVo> returnListGoodsVos = new ArrayList<>();

        list.forEach(saleList -> {

            CustomerReturnListGoodsVo returnListGoodsVo = new CustomerReturnListGoodsVo();
            BeanUtils.copyProperties(saleList,returnListGoodsVo);

            Customer customer = customerDao.getCustomerById(saleList.getCustomerId());
            returnListGoodsVo.setCustomerName(customer.getCustomerName());

            User user = userDao.getUserById(saleList.getUserId());
            returnListGoodsVo.setTrueName(user.getTrueName());
            returnListGoodsVos.add(returnListGoodsVo);

        });

        return returnListGoodsVos;
    }

    @Override
    public List<CountSaleGoodsVo> countReturnListGoods(CountQueryParam countQueryParam) {

        List<CountSaleGoodsVo> list = customerReturnListGoodsDao.countSaleListGoods(countQueryParam);
        return list;

    }
}
