package com.meijinzhi.apply.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meijinzhi.apply.entity.BigDecimal;
import com.meijinzhi.apply.entity.PostInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BigDecimalMapper extends BaseMapper<BigDecimal> {
    List<PostInfo> getBigDecimal(String result);
    List<BigDecimal> getByUsername(String username);
}
