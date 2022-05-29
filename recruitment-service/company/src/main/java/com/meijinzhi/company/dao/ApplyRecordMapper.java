package com.meijinzhi.company.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meijinzhi.company.entity.ApplyRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApplyRecordMapper extends BaseMapper<ApplyRecord> {
    List<ApplyRecord>getApplyByCompany (long id);
}
