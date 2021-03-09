package com.atguigu.jxc.vo;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

@Data
public class ReturnGoodsVo {
    private String returnNumber;
    private Integer supplierId;
    private Integer state;
    private String sTime;
    private String eTime;
}