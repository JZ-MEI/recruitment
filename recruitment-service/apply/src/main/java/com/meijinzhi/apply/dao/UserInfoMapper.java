package com.meijinzhi.apply.dao;

import com.meijinzhi.apply.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserInfoMapper {
    int signIn(@Param("username") String username, @Param("password") String password,  @Param("email") String targetEmail);

    UserInfo selectByUsername(String username);

    int updatePassword(@Param("username") String username, @Param("password") String password);
}
