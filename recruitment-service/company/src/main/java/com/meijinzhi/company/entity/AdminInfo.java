package com.meijinzhi.company.entity;


import lombok.Data;

@Data
public class AdminInfo {

    private long id;
    private String adminName;
    private String password;
    private String isSuper;
    private String isAudit;
    private String email;

}
