package com.meijinzhi.company.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meijinzhi.company.entity.CompanyInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CompanyInfoMapper extends BaseMapper<CompanyInfo> {
    CompanyInfo selectByUsername(String username);

    int signIn(@Param("username") String username, @Param("password") String password, @Param("email") String targetEmail);

    int updatePassword(@Param("username") String username, @Param("password") String password);

    List<CompanyInfo> getCompanyInfo(@Param("start") int start, @Param("limit") int limit,@Param("keyWord")String keyWord);

    List<CompanyInfo> getCompanyInfoCount(@Param("keyWord")String keyWord);

    int audit(@Param("username") String username, @Param("auditTime") String auditTime, @Param("companyId") String companyId);

    String selectLogoById(long id);

    String selectCompanyNameById(long id);

    int auditPost(@Param("username") String username, @Param("auditTime") String auditTime, @Param("postId") String companyId);
}
