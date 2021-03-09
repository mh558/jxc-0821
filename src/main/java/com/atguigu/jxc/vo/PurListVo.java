package com.atguigu.jxc.vo;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

@Data
public class PurListVo {
   private String sTime;
   private String eTime ;
   private Integer goodsTypeId;
   private String codeOrName;
}