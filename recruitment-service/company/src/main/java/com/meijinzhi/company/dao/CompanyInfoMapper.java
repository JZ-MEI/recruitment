package com.meijinzhi.company.dao;

import com.meijinzhi.company.entity.CompanyInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CompanyInfoMapper {
    CompanyInfo selectByUsername(String username) ;
    int signIn(@Param("username") String username, @Param("password") String password, @Param("email") String targetEmail);
    int updatePassword(@Param("username") String username, @Param("password") String password);
    List<CompanyInfo> getCompanyInfo(@Param("start") int start,@Param("limit") int limit);
    List<CompanyInfo> getCompanyInfoCount();

}
