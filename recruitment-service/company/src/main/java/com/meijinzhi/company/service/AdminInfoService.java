package com.meijinzhi.company.service;

import com.meijinzhi.company.dao.AdminInfoMapper;
import com.meijinzhi.company.entity.AdminInfo;
import com.meijinzhi.company.entity.CompanyInfo;

import java.util.List;

public interface AdminInfoService {
    boolean LogIn(AdminInfo adminInfo);
    AdminInfo getAdmin(String username);
    Boolean signIn(AdminInfo adminInfo);
    Boolean forget(AdminInfo adminInfo);
    List<CompanyInfo> getCompanyInfo(int page,int limit);
    int getCompanyInfoCount();
    void audit(String username,long companyId);
}
