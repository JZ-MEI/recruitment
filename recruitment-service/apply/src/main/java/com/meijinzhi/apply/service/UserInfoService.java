package com.meijinzhi.apply.service;


import com.meijinzhi.apply.entity.ResumeInfo;
import com.meijinzhi.apply.entity.UserInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserInfoService {
    Boolean signIn(UserInfo userInfo);
    Boolean LogIn(UserInfo userInfo);
    Boolean forget(UserInfo userInfo);
    void setResume(ResumeInfo resumeInfo, String[] companyName, String[] startTime, String[] endTime, String[] workDescribe, String username);
    void updatePhoto(MultipartFile file) throws Exception;
    ResumeInfo getResume(String username);
    void uploadResume(MultipartFile file,String username) throws IOException;
}
