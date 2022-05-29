package com.meijinzhi.apply.entity;

import lombok.Data;

@Data
public class UserInfo {
    /**
     * 用户ID
     */
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
     * 邮箱
     */
    private String email;
    /**
     * 照片，Base64
     */
    private String photo;
    /**
     * 电子简历，Base64
     */
    private String resume;
}
