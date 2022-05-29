package com.meijinzhi.apply.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meijinzhi.apply.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserInfoMapper  extends BaseMapper<UserInfo> {
    int signIn(@Param("username") String username, @Param("password") String password, @Param("email") String targetEmail);

    UserInfo selectByUsername(String username);

    int updatePassword(@Param("username") String username, @Param("password") String password);
}
