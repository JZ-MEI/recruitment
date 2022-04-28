package com.meijinzhi.apply.service;


import com.meijinzhi.apply.entity.UserInfo;

public interface UserInfoService {
    Boolean signIn(UserInfo userInfo);
    Boolean LogIn(UserInfo userInfo);
    Boolean forget(UserInfo userInfo);
}
