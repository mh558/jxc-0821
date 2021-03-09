package com.atguigu.jxc.vo;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.PrivateKey;

@Data
public class PurchaseGoodsVo {
    private String purchaseNumber;
    private Integer supplierId;
    private Integer state;
    private String sTime;
    private String eTime;
}