package com.atguigu.jxc.dao;

import com.atguigu.jxc.entity.SaleListGoods;
import com.atguigu.jxc.vo.SaleListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SaleListGoodsDao {

    Integer getSaleDataByDay( List<Integer> saleListIds);

    List<SaleListGoods> list(List<Integer> saleListIds);
}