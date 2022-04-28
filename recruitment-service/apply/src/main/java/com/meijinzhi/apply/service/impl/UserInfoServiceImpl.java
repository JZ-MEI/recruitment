package com.meijinzhi.apply.service.impl;


import com.meijinzhi.apply.dao.UserInfoMapper;
import com.meijinzhi.apply.entity.UserInfo;
import com.meijinzhi.apply.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    private Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);
    @Autowired
    UserInfoMapper userInfoMapper;
    @Override
    public Boolean signIn(UserInfo userInfo) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userInfoMapper.signIn(userInfo.getUsername(), passwordEncoder.encode(userInfo.getPassword()), userInfo.getEmail());
        return true;
    }

    @Override
    public Boolean LogIn(UserInfo userInfo) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserInfo signedUser = userInfoMapper.selectByUsername(userInfo.getUsername());
        if (signedUser!=null){
            return passwordEncoder.matches(userInfo.getPassword(),signedUser.getPassword());
        }else{
            return false;
        }
    }

    @Override
    public Boolean forget(UserInfo userInfo) {
        UserInfo signedUser = userInfoMapper.selectByUsername(userInfo.getUsername());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (signedUser.getEmail().equals(userInfo.getEmail())){
            userInfoMapper.updatePassword(userInfo.getUsername(),passwordEncoder.encode(userInfo.getPassword()));
            return true;
        }else{
            return false;
        }
    }
}
