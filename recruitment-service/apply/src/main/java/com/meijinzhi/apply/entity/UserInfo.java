package com.meijinzhi.apply.entity;

import lombok.Data;

@Data
public class UserInfo {
    private long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 电子邮箱
     */
    private String email;
    /**
     * 照片
     */
    private String photo;
    /**
     * 在线简历
     */
    private String resume;
}
