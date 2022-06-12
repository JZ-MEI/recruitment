package com.meijinzhi.apply.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meijinzhi.apply.entity.ApplyRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ApplyRecordMapper extends BaseMapper<ApplyRecord> {
    int insertApplyRecord(ApplyRecord applyRecord);
    List<ApplyRecord> getRecordByUserId(@Param("userId") String userId,@Param("start")int start,@Param("limit")int limit);
    List<ApplyRecord> getRecordCount(String userId);
}
