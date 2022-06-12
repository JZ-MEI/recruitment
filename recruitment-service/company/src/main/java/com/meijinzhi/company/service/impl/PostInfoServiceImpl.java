package com.meijinzhi.company.service.impl;

import com.meijinzhi.company.dao.ApplyRecordMapper;
import com.meijinzhi.company.dao.CompanyInfoMapper;
import com.meijinzhi.company.dao.PostInfoMapper;
import com.meijinzhi.company.dao.ResumeInfoMapper;
import com.meijinzhi.company.entity.ApplyRecord;
import com.meijinzhi.company.entity.CompanyInfo;
import com.meijinzhi.company.entity.PostInfo;
import com.meijinzhi.company.entity.ResumeInfo;
import com.meijinzhi.company.service.PostInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostInfoServiceImpl implements PostInfoService {
    @Autowired
    PostInfoMapper postInfoMapper;
    @Autowired
    CompanyInfoMapper companyInfoMapper;
    @Autowired
    ApplyRecordMapper applyRecordMapper;
    @Autowired
    ResumeInfoMapper resumeInfoMapper;
    @Override
    public List<PostInfo> getMyPost(String username,String keyWord,int page,int limit) {
        int start = 0;
        if (page != 1) {
            start = (page - 1) * limit;
        }
        long id = companyInfoMapper.selectByUsername(username).getId();
        return postInfoMapper.getMyPost(id,keyWord,start,limit);
    }

    @Override
    public int getCount(String username, String keyWord) {
        long id = companyInfoMapper.selectByUsername(username).getId();
        return postInfoMapper.getPostCount(id,keyWord).size();
    }

    @Override
    public void updatePost( PostInfo postInfo) {
        postInfoMapper.updateById(postInfo);
    }

    @Override
    public List<ResumeInfo> getApply(String username) {
        List<ApplyRecord> applyRecords = applyRecordMapper.getApplyByCompany(companyInfoMapper.selectByUsername(username).getId());
        List<ResumeInfo> resumeInfos = new ArrayList<>();
        for (ApplyRecord applyRecord:applyRecords){
            PostInfo postInfo = postInfoMapper.getPostById(String.valueOf(applyRecord.getPostId()));
            ResumeInfo resumeInfo = resumeInfoMapper.selectByUser(String.valueOf(applyRecord.getUserId()));
            resumeInfo.setPost(postInfo.getPost());
            resumeInfo.setResumeFile("http://localhost:9000/recruitment/resume/"+applyRecord.getUserId()+".pdf");
            resumeInfos.add(resumeInfo);
        }
        return resumeInfos;
    }

    @Override
    public CompanyInfo getCompanyInfo(String username) {
        return companyInfoMapper.selectByUsername(username);
    }

    @Override
    public void updatePush(String username) {
        CompanyInfo companyInfo=new CompanyInfo();
        CompanyInfo companyInfo1 = companyInfoMapper.selectByUsername(username);
        companyInfo.setId(companyInfo1.getId());
        companyInfo.setIsPush("1");
        companyInfoMapper.updateById(companyInfo);
    }

    @Override
    public void add(PostInfo postInfo, String company) {
        CompanyInfo companyInfo = companyInfoMapper.selectByUsername(company);
        postInfo.setCompanyId(String.valueOf(companyInfo.getId()));
        postInfoMapper.insertPost(postInfo);
    }
}
