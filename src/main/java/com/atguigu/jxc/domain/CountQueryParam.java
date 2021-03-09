package com.atguigu.jxc.domain;

import lombok.Data;

@Data
public class CountQueryParam {

    public String sTime;
    public String eTime;
    public Integer goodsTypeId;
    public String codeOrName;

}
