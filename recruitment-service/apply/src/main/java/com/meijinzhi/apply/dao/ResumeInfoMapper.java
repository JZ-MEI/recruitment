package com.meijinzhi.apply.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meijinzhi.apply.entity.ResumeInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ResumeInfoMapper extends BaseMapper<ResumeInfo> {
    ResumeInfo selectByUserId(long userId);
}
