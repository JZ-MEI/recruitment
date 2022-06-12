package com.meijinzhi.company.service.impl;

import com.meijinzhi.company.dao.AdminInfoMapper;
import com.meijinzhi.company.dao.CarouselImgMapper;
import com.meijinzhi.company.dao.CompanyInfoMapper;
import com.meijinzhi.company.dao.PostInfoMapper;
import com.meijinzhi.company.entity.AdminInfo;
import com.meijinzhi.company.entity.CarouselImg;
import com.meijinzhi.company.entity.CompanyInfo;
import com.meijinzhi.company.entity.PostInfo;
import com.meijinzhi.company.service.AdminInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AdminInfoServiceImpl implements AdminInfoService {
    @Autowired
    AdminInfoMapper adminInfoMapper;
    @Autowired
    CompanyInfoMapper companyInfoMapper;
    @Autowired
    PostInfoMapper postInfoMapper;
    @Autowired
    CarouselImgMapper carouselImgMapper;
    @Override
    public boolean LogIn(AdminInfo adminInfo) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        AdminInfo signedUser = adminInfoMapper.selectByUsername(adminInfo.getAdminName());
        if (signedUser != null) {
            return passwordEncoder.matches(adminInfo.getPassword(), signedUser.getPassword());
        } else {
            return false;
        }
    }

    @Override
    public AdminInfo getAdmin(String username) {
        return adminInfoMapper.selectByUsername(username);
    }
    @Override
    public Boolean signIn(AdminInfo adminInfo) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(adminInfo.getPassword());
        adminInfo.setPassword(password);
        adminInfoMapper.insert(adminInfo);
        return true;
    }
    @Override
    public Boolean forget(AdminInfo adminInfo) {
        AdminInfo signedUser = adminInfoMapper.selectByUsername(adminInfo.getAdminName());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (signedUser.getEmail().equals(adminInfo.getEmail())) {
            adminInfo.setId(signedUser.getId());
            String password = passwordEncoder.encode(adminInfo.getPassword());
            adminInfo.setPassword(password);
            adminInfoMapper.updateById(adminInfo);
//            companyInfoMapper.updatePassword(adminInfo.getUsername(), passwordEncoder.encode(companyInfo.getPassword()));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<CompanyInfo> getCompanyInfo(int page,int limit,String keyWord) {
        int start = 0;
        if (page != 1) {
            start = (page - 1) * limit;
        }
        return companyInfoMapper.getCompanyInfo(start,limit,keyWord);
    }

    @Override
    public int getCompanyInfoCount(String keyWord) {
        return companyInfoMapper.getCompanyInfoCount(keyWord).size();
    }

    @Override
    public void audit(String username, long companyId) {
        Date date=new Date();
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        String auditTime = df.format(date);
        companyInfoMapper.audit(username,auditTime,String.valueOf(companyId));
    }
    @Override
    public List<PostInfo> getAllPost(String isAudit,int page, int limit,String keyWord) {
        int start = 0;
        if (page != 1) {
            start = (page - 1) * limit;
        }
        List<PostInfo> allPost = postInfoMapper.getAllPost(isAudit,start, limit,keyWord);
        List<PostInfo> postInfos=new ArrayList<>();
        for(PostInfo postInfo:allPost){
            postInfo.setCompanyName(companyInfoMapper.selectCompanyNameById(Long.parseLong(postInfo.getCompanyId())));
            postInfos.add(postInfo);
        }
        return postInfos;
    }

    @Override
    public int getAllPostCount(String isAudit,String keyWord) {
        return postInfoMapper.getAllPostCount(isAudit,keyWord).size();
    }

    @Override
    public void auditPost(String username, long postId) {
        Date date=new Date();
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        String auditTime = df.format(date);
        companyInfoMapper.auditPost(username,auditTime,String.valueOf(postId));
    }

    @Override
    public List<AdminInfo> selectAdmin(int page, int limit, String keyWord) {
        int start = 0;
        if (page != 1) {
            start = (page - 1) * limit;
        }
        return adminInfoMapper.getAdmin(start,limit,keyWord);
    }

    @Override
    public int getAdminCount(String keyWord) {
        return adminInfoMapper.getAdminCount(keyWord).size();
    }

    @Override
    public void adminAudit(long id,String type) {
        AdminInfo adminInfo = new AdminInfo();
        adminInfo.setId(id);
        if (type.equals("audit")) {
            adminInfo.setIsAudit("1");
        }else {
            adminInfo.setIsSuper("1");
        }
        adminInfoMapper.updateById(adminInfo);
    }

    @Override
    public void upload(MultipartFile file, String index) throws IOException {
        String uploadPath="D:\\DevelopSoft\\apache-tomcat-8.5.78\\webapps\\recruitment\\banner";
        String substring = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName=index+substring;
        File localFile=new File(uploadPath+"\\"+fileName);
        file.transferTo(localFile);
        CarouselImg carouselImg=new CarouselImg();
        carouselImg.setId(Long.parseLong(index));
        carouselImg.setImg("http://localhost:9000/recruitment/banner/"+fileName);
        carouselImgMapper.updateById(carouselImg);
    }
}
