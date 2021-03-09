package com.atguigu.jxc.vo;

import lombok.Data;

@Data
public class CountPurchaseVo {
/*
*       "number": "JH1605773419636",
        "date": "2020-11-19",
        *
        *
        "supplierName": "四川耀荣汇商贸有限公司",
        *
        *
        "code": "0006",
        "name": "冰糖金桔干",
        "model": "300g装",
        "unit": "盒",
        "price": 5,
        "num": 5,
        "total": 25
        *
        *
        "goodsType": "休闲食品",

*/
    private String number;

    private String date;

    private String supplierName;

    private String code;

    private String name;

    private String model;

    private String goodsType;

    private String unit;

    private double price;

    private double num;

    private double total;
}
