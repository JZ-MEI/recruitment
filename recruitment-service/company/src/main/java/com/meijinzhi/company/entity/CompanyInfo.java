package com.meijinzhi.company.entity;

import lombok.Data;

@Data
public class CompanyInfo {

  private long id;
  private String username;
  private String password;
  private String email;
  private String companyName;
  private String address;
  private String logo;
  private String license;
  private String isAudit;
  private String isPush;
  private String auditUser;
  private String auditTime;
}
