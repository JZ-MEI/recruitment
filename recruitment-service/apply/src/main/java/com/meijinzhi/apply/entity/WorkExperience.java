package com.meijinzhi.apply.entity;

import lombok.Data;

@Data
public class WorkExperience {

  private long id;
  private long userId;
  private String companyName;
  private String startTime;
  private String endTime;
  private String workDescribe;
}
