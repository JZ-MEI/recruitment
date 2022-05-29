package com.meijinzhi.apply.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CompanyInfoMapper {
    String selectLogoById(long id);
    String selectCompanyNameById(long id);
    String companyName(String companyId);
}
