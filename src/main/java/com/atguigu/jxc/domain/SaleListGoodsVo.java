package com.atguigu.jxc.domain;

import lombok.Data;

@Data
public class SaleListGoodsVo {


    public Integer saleListId;
    public String saleNumber;
    public double amountPaid;
    public double amountPayable;
    public String saleDate;
    public Integer state;
    public Integer remarks;
    public Integer customerId;
    public Integer userId;
    public String customerName;
    public String trueName;


}
