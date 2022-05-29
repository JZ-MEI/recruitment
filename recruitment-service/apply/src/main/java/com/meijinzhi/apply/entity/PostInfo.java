package com.meijinzhi.apply.entity;

import lombok.Data;

@Data
public class PostInfo {

    private long id;
    private long companyId;
    private String pay;
    private String post;
    private String workDescribe;
    private String workplace;
    private long recruitingNumber;
    private String postType;
    private String logo;
    private String companyName;
}
