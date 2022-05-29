package com.meijinzhi.company.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meijinzhi.company.entity.ResumeInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ResumeInfoMapper extends BaseMapper<ResumeInfo> {
    ResumeInfo selectByUser(String userId);
}
