package com.atguigu.jxc.dao;

import com.atguigu.jxc.entity.PurchaseList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PurchaseListDao {
    Integer save(PurchaseList purchaseList);

    List<PurchaseList> list(@Param("purchaseNumber") String purchaseNumber,
                            @Param("supplierId") Integer supplierId,
                            @Param("state") Integer state,
                            @Param("sTime") String sTime,
                            @Param("eTime") String eTime);

    Integer deletePurchaseList(Integer purchaseListId);
}
