package com.meijinzhi.company.entity;

import lombok.Data;

@Data
public class PostInfo {

  private long id;
  private String companyId;
  private String pay;
  private String post;
  private String workDescribe;
  private String workplace;
  private String recruitingNumber;
  private String postType;
  private String isAudit;
  private String auditName;
  private String auditTime;
  private String companyName;

}
