package com.atguigu.jxc.dao;

import com.atguigu.jxc.domain.CountQueryParam;
import com.atguigu.jxc.domain.CountSaleGoodsVo;
import com.atguigu.jxc.domain.QueryParam;
import com.atguigu.jxc.entity.SaleList;
import com.atguigu.jxc.entity.SaleListGoods;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SaleListGoodsDao {

    void saveSaleList(SaleList saleList1);

    void saveSaleListGoods(SaleListGoods saleListGoods);

    List<SaleList> querSaleyList(QueryParam queryParam);

    List<SaleListGoods> querySaleListGoods(Integer saleListId);

    void deleteSaleList(Integer saleListId);

    void deleteSaleListGoods(Integer saleListId);

    void updateState(Integer saleListId);

    List<CountSaleGoodsVo> countSaleListGoods(CountQueryParam countQueryParam);

    Integer getSaleDataByDay( List<Integer> saleListIds);

    List<SaleListGoods> list(List<Integer> saleListIds);
}
