package com.meijinzhi.company.service;

import com.meijinzhi.company.dao.AdminInfoMapper;
import com.meijinzhi.company.entity.AdminInfo;
import com.meijinzhi.company.entity.CompanyInfo;
import com.meijinzhi.company.entity.PostInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AdminInfoService {
    boolean LogIn(AdminInfo adminInfo);

    AdminInfo getAdmin(String username);

    Boolean signIn(AdminInfo adminInfo);

    Boolean forget(AdminInfo adminInfo);

    List<CompanyInfo> getCompanyInfo(int page, int limit, String keyWord);

    int getCompanyInfoCount(String keyWord);

    void audit(String username, long companyId);

    List<PostInfo> getAllPost(String isAudit, int page, int limit, String keyWord);

    int getAllPostCount(String isAudit, String keyWord);

    void auditPost(String username, long postId);

    List<AdminInfo> selectAdmin(int page, int limit, String keyWord);

    int getAdminCount(String keyWord);

    void adminAudit(long id, String type);

    void upload(MultipartFile file, String index) throws IOException;
}
