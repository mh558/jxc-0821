package com.atguigu.jxc.dao;

import com.atguigu.jxc.entity.ReturnList;
import com.atguigu.jxc.vo.ReturnGoodsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReturnListDao {
    void saveReturnList(ReturnList returnList);

    List<ReturnList> list(ReturnGoodsVo returnGoodsVo);

    Integer deleteReturnList(Integer returnListId);

    ReturnList getById(@Param("returnListId") Integer returnListId);

}
