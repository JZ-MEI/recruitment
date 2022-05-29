package com.meijinzhi.company.entity;

import lombok.Data;

@Data
public class UserInfo {

  private long id;
  private String username;
  private String password;
  private String email;
  private String photo;
  private String resume;

}
