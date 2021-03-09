package com.atguigu.jxc.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class SaleListVo {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date data = new Date();
    private Integer saleTotal;
    private Integer purchasingTotal;
    private Integer profit;
}