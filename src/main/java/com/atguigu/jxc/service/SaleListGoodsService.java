package com.atguigu.jxc.service;

import com.atguigu.jxc.domain.CountQueryParam;
import com.atguigu.jxc.domain.CountSaleGoodsVo;
import com.atguigu.jxc.domain.QueryParam;
import com.atguigu.jxc.domain.SaleListGoodsVo;
import com.atguigu.jxc.entity.SaleList;
import com.atguigu.jxc.entity.SaleListGoods;

import java.util.List;

public interface SaleListGoodsService {
    void saveSaleListGoods(String saleNumber, SaleList saleList, String saleListGoodsStr);

    List<SaleListGoodsVo> querSaleyList(QueryParam queryParam);

    List<SaleListGoods> querySaleListGoods(Integer saleListId);

    void deleteSaleListGoods(Integer saleListId);

    void updateState(Integer saleListId);

    List<CountSaleGoodsVo> countSaleListGoods(CountQueryParam countQueryParam);
}
