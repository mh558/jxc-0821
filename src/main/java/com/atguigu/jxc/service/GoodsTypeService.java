package com.atguigu.jxc.service;

import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.entity.GoodsType;

/**
 * @description
 */
public interface GoodsTypeService {

    ServiceVO delete(Integer goodsTypeId);

    ServiceVO save(String goodsTypeName,Integer pId);

    String loadGoodsType();

    GoodsType getGoodsTypeById(Integer goodsTypeId);
}
