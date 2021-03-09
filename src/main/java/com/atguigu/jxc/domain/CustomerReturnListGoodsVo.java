package com.atguigu.jxc.domain;

import lombok.Data;

@Data
public class CustomerReturnListGoodsVo {

    public Integer customerReturnListId;
    public String returnNumber;
    public double amountPaid;
    public double amountPayable;
    public String returnDate;
    public Integer state;
    public Integer remarks;
    public Integer customerId;
    public Integer userId;
    public String customerName;
    public String trueName;

}
