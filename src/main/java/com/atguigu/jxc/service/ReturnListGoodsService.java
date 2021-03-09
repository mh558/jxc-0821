package com.atguigu.jxc.service;

import com.atguigu.jxc.entity.ReturnList;
import com.atguigu.jxc.entity.ReturnListGoods;
import com.atguigu.jxc.vo.PurListVo;
import com.atguigu.jxc.vo.ReturnGoodsVo;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface ReturnListGoodsService {
    void saveReturnListGoods(String returnNumber, ReturnList returnList, String returnListGoodsStr, HttpSession session);

    List<ReturnList> list(ReturnGoodsVo returnGoodsVo);

    List<ReturnListGoods> goodsList(Integer returnListId);

    void deleteReturnList(Integer returnListId);

    String count(PurListVo purListVo);

    List<ReturnListGoods> listByDay(List<Integer> collect);
}
