package com.meijinzhi.apply.entity;

import lombok.Data;

@Data
public class CompanyInfo {

    private long id;
    private String username;
    private String password;
    private String email;
    private String companyName;
    private String address;

}
