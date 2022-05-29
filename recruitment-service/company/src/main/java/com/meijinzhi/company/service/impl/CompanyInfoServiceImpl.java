package com.meijinzhi.company.service.impl;

import com.meijinzhi.company.dao.CompanyInfoMapper;
import com.meijinzhi.company.entity.CompanyInfo;
import com.meijinzhi.company.service.CompanyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
}
