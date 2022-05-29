package com.meijinzhi.company.service.impl;

import com.meijinzhi.company.dao.AdminInfoMapper;
import com.meijinzhi.company.dao.CompanyInfoMapper;
import com.meijinzhi.company.entity.AdminInfo;
import com.meijinzhi.company.entity.CompanyInfo;
import com.meijinzhi.company.service.AdminInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminInfoServiceImpl implements AdminInfoService {
    @Autowired
    AdminInfoMapper adminInfoMapper;
    @Autowired
    CompanyInfoMapper companyInfoMapper;
    @Override
    public boolean LogIn(AdminInfo adminInfo) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        AdminInfo signedUser = adminInfoMapper.selectByUsername(adminInfo.getAdminName());
        if (signedUser != null) {
            return passwordEncoder.matches(adminInfo.getPassword(), signedUser.getPassword());
        } else {
            return false;
        }
    }

    @Override
    public AdminInfo getAdmin(String username) {
        return adminInfoMapper.selectByUsername(username);
    }
    @Override
    public Boolean signIn(AdminInfo adminInfo) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(adminInfo.getPassword());
        adminInfo.setPassword(password);
        adminInfoMapper.insert(adminInfo);
        return true;
    }
    @Override
    public Boolean forget(AdminInfo adminInfo) {
        AdminInfo signedUser = adminInfoMapper.selectByUsername(adminInfo.getAdminName());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (signedUser.getEmail().equals(adminInfo.getEmail())) {
            adminInfo.setId(signedUser.getId());
            String password = passwordEncoder.encode(adminInfo.getPassword());
            adminInfo.setPassword(password);
            adminInfoMapper.updateById(adminInfo);
//            companyInfoMapper.updatePassword(adminInfo.getUsername(), passwordEncoder.encode(companyInfo.getPassword()));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<CompanyInfo> getCompanyInfo(int page,int limit) {
        int start = 0;
        if (page != 1) {
            start = (page - 1) * limit;
        }
        return companyInfoMapper.getCompanyInfo(start,limit);
    }

    @Override
    public int getCompanyInfoCount() {
        return companyInfoMapper.getCompanyInfoCount().size();
    }

    @Override
    public void audit(String username, long companyId) {

    }
}
