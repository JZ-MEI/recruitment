package com.meijinzhi.company.service;

import com.meijinzhi.company.entity.CompanyInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CompanyInfoService {
    Boolean LogIn(CompanyInfo companyInfo);

    Boolean signIn(CompanyInfo companyInfo);

    Boolean forget(CompanyInfo companyInfo);

    void updateCompanyInfo(CompanyInfo companyInfo);

    void updateLogo(MultipartFile file, String username) throws IOException;

    void updateLicense(MultipartFile file, String username) throws IOException;

    CompanyInfo getCompanyInfo(String username);
}
