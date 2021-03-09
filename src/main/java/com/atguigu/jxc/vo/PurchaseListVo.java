package com.atguigu.jxc.vo;

import lombok.Data;

@Data
public class PurchaseListVo {
    /*
    *        "purchaseListId": 133,
            "purchaseNumber": "JH1605773419636",
            "amountPaid": 25,
            "amountPayable": 25,
            "purchaseDate": "2020-11-19",
            "remarks": "",
            "state": 1,
            "supplierId": 1,
            "userId": 1,
            "supplierName": "四川耀荣汇商贸有限公司",
            "trueName": "兰杰"
*/

    private Integer purchaseListId;

    private String purchaseNumber;

    private double amountPaid;

    private double amountPayable;

    private String purchaseDate;

    private String remarks;

    private Integer state;

    private Integer supplierId;

    private Integer userId;

    private String supplierName;

    private String trueName;
}
