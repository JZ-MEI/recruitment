package com.meijinzhi.apply.entity;

import lombok.Data;

@Data
public class ResumeInfo {

    private long id;
    private String name;
    private String sex;
    private String telephone;
    private String email;
    private long workExperienceId;
    private String personalIntroduction;
    private String salaryExpectation;
    private long userId;
    private String photo;
}
