package com.meijinzhi.apply.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meijinzhi.apply.entity.ApplyRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApplyRecordMapper extends BaseMapper<ApplyRecord> {
    int insertApplyRecord(ApplyRecord applyRecord);
}
