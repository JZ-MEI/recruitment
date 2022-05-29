package com.meijinzhi.apply.entity;

import lombok.Data;

@Data
public class City {

    private long cityId;
    private String cityName;
    private long provinceId;
    private int isHot;

}
