package com.atguigu.jxc.dao;

import com.atguigu.jxc.entity.PurchaseListGoods;
import com.atguigu.jxc.entity.ReturnList;
import com.atguigu.jxc.entity.ReturnListGoods;
import com.atguigu.jxc.vo.PurListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReturnListGoodsDao {
    void saveReturnListGoods(@Param("returnListGoods") List<ReturnListGoods> returnListGoods);

    List<ReturnListGoods> goodsList( @Param("returnListId") Integer returnListId);

    Integer deleteReturnList(Integer returnListId);

    List<ReturnListGoods> count(@Param("list") List<Integer> list, @Param("purListVo") PurListVo purListVo);
}
