package com.atguigu.jxc.vo;

import lombok.Data;

@Data
public class ReturnListVo {

    private Integer returnListId;

    private String returnNumber;

    private double amountPaid;

    private double amountPayable;

    private String returnDate;

    private String remarks;

    private Integer state;

    private Integer supplierId;

    private Integer userId;

    private String supplierName;

    private String trueName;
}
