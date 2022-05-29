package com.meijinzhi.company.entity;

import lombok.Data;

@Data
public class WorkExperience {

  private long id;
  private long userId;
  private String companyName;
  private java.sql.Timestamp startTime;
  private java.sql.Timestamp endTime;
  private String workDescribe;

}
