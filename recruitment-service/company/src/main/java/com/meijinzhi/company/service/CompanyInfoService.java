package com.meijinzhi.company.service;

import com.meijinzhi.company.entity.CompanyInfo;

public interface CompanyInfoService {
    Boolean LogIn(CompanyInfo companyInfo);
    Boolean signIn(CompanyInfo companyInfo);
    Boolean forget(CompanyInfo companyInfo);
}
