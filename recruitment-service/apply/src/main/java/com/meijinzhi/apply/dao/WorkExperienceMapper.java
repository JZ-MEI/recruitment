package com.meijinzhi.apply.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meijinzhi.apply.entity.WorkExperience;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WorkExperienceMapper extends BaseMapper<WorkExperience> {
    List<WorkExperience> selectByUserId(long userId);
}
