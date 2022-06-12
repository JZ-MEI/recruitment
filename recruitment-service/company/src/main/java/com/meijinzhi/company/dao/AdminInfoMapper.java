package com.meijinzhi.company.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meijinzhi.company.entity.AdminInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminInfoMapper extends BaseMapper<AdminInfo> {
    AdminInfo selectByUsername(String username);
    List<AdminInfo> getAdmin(@Param("start")int start,@Param("limit")int limit,@Param("keyWord")String keyWord);
    List<AdminInfo> getAdminCount(@Param("keyWord")String keyWord);
}
