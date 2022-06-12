package com.meijinzhi.company.service.impl;

import com.meijinzhi.company.dao.CompanyInfoMapper;
import com.meijinzhi.company.entity.CompanyInfo;
import com.meijinzhi.company.service.CompanyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class CompanyInfoServiceImpl implements CompanyInfoService {
    @Autowired
    CompanyInfoMapper companyInfoMapper;
    @Override
    public Boolean LogIn(CompanyInfo companyInfo) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        CompanyInfo signedUser = companyInfoMapper.selectByUsername(companyInfo.getUsername());
        if (signedUser != null) {
            return passwordEncoder.matches(companyInfo.getPassword(), signedUser.getPassword());
        } else {
            return false;
        }
    }
    @Override
    public Boolean signIn(CompanyInfo companyInfo) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        companyInfoMapper.signIn(companyInfo.getUsername(), passwordEncoder.encode(companyInfo.getPassword()), companyInfo.getEmail());
        return true;
    }
    @Override
    public Boolean forget(CompanyInfo companyInfo) {
        CompanyInfo signedUser = companyInfoMapper.selectByUsername(companyInfo.getUsername());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (signedUser.getEmail().equals(companyInfo.getEmail())) {
            companyInfoMapper.updatePassword(companyInfo.getUsername(), passwordEncoder.encode(companyInfo.getPassword()));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void updateCompanyInfo(CompanyInfo companyInfo) {
        long id = companyInfoMapper.selectByUsername(companyInfo.getUsername()).getId();
        companyInfo.setId(id);
        companyInfo.setIsAudit("0");
        companyInfo.setIsPush("0");
        companyInfoMapper.updateById(companyInfo);
    }

    @Override
    public void updateLogo(MultipartFile file, String username) throws IOException {
        long id = companyInfoMapper.selectByUsername(username).getId();
        String uploadPath="D:\\DevelopSoft\\apache-tomcat-8.5.78\\webapps\\recruitment\\companyLogo";
        String substring = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName=id+substring;
        File localFile=new File(uploadPath+"\\"+fileName);
        file.transferTo(localFile);
        CompanyInfo companyInfo = new CompanyInfo();
        companyInfo.setId(id);
        companyInfo.setLogo("http://localhost:9000/recruitment/companyLogo/"+fileName);
        companyInfoMapper.updateById(companyInfo);
    }

    @Override
    public void updateLicense(MultipartFile file, String username) throws IOException {
        long id = companyInfoMapper.selectByUsername(username).getId();
        String uploadPath="D:\\DevelopSoft\\apache-tomcat-8.5.78\\webapps\\recruitment\\license";
        String substring = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName=id+substring;
        File localFile=new File(uploadPath+"\\"+fileName);
        file.transferTo(localFile);
        CompanyInfo companyInfo = new CompanyInfo();
        companyInfo.setId(id);
        companyInfo.setLicense("http://localhost:9000/recruitment/license/"+fileName);
        companyInfoMapper.updateById(companyInfo);
    }

    @Override
    public CompanyInfo getCompanyInfo(String username) {
        return companyInfoMapper.selectByUsername(username);
    }
}
