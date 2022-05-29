package com.meijinzhi.apply.service;

import com.meijinzhi.apply.entity.PostInfo;

import java.util.List;

public interface BigDecimalService {
    void insertResult(String username,String result);
    List<PostInfo> getBigDecimal(String username);
}
