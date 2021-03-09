package com.atguigu.jxc.service;

import com.atguigu.jxc.entity.ReturnList;
import com.atguigu.jxc.vo.DateParam;
import com.atguigu.jxc.vo.ReturnGoodsVo;

import java.util.List;

public interface ReturnListService {
    List<ReturnList> getSaleDataByDay(DateParam dataVo);

    void saveReturnList(ReturnList returnList);

    List<ReturnList> list(ReturnGoodsVo returnGoodsVo);

    void deleteReturnList(Integer returnListId);

    ReturnList getById(Integer returnListId);
}
