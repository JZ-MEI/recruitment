package com.meijinzhi.company.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meijinzhi.company.entity.AdminInfo;
import com.meijinzhi.company.entity.CompanyInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminInfoMapper extends BaseMapper<AdminInfo> {
    AdminInfo selectByUsername(String username) ;
}
