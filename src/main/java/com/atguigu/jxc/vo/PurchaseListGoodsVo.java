package com.atguigu.jxc.vo;

import lombok.Data;
import org.apache.ibatis.annotations.Param;

@Data
public class PurchaseListGoodsVo {
    private String number;
    private String date;
    private String supplierName;
    private String code;
    private String name;
    private String model;
    private String goodsType;
    private String unit;
    private double price;
    private double total;
    private Integer num;
}