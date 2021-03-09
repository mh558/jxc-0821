package com.atguigu.jxc.dao;

import com.atguigu.jxc.entity.SaleList;
import com.atguigu.jxc.vo.DateParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SaleListDao {
    List<SaleList> getSaleDataByDay(DateParam dataVo);
}