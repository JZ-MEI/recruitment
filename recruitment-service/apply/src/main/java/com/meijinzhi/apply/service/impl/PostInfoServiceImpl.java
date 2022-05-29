package com.meijinzhi.apply.service.impl;

import com.meijinzhi.apply.dao.CompanyInfoMapper;
import com.meijinzhi.apply.dao.PostInfoMapper;
import com.meijinzhi.apply.entity.PostInfo;
import com.meijinzhi.apply.service.PostInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PostInfoServiceImpl implements PostInfoService {
    @Autowired
    PostInfoMapper postInfoMapper;
    @Autowired
    CompanyInfoMapper companyInfoMapper;
    @Override
    public List<PostInfo> getPostByType(String keyWord,String type) {
        List<PostInfo> postInfos=new ArrayList<>();
        List<PostInfo> postInfoList=new ArrayList<>();
        if(keyWord!=null){
            postInfos = postInfoMapper.getPostByKeyWord(keyWord);
            for (PostInfo postInfo:postInfos){
                postInfo.setCompanyName(companyInfoMapper.selectCompanyNameById(postInfo.getCompanyId()));
                postInfo.setLogo(companyInfoMapper.selectLogoById(postInfo.getCompanyId()));
                postInfoList.add(postInfo);
            }
        }else if(type!=null){
            postInfos = postInfoMapper.getAllPostByType(type);
            for (PostInfo postInfo:postInfos){
                postInfo.setCompanyName(companyInfoMapper.selectCompanyNameById(postInfo.getCompanyId()));
                postInfo.setLogo(companyInfoMapper.selectLogoById(postInfo.getCompanyId()));
                postInfoList.add(postInfo);
            }
        }else{
            postInfos = postInfoMapper.getAllPost();
            for (PostInfo postInfo:postInfos){
                postInfo.setCompanyName(companyInfoMapper.selectCompanyNameById(postInfo.getCompanyId()));
                postInfo.setLogo(companyInfoMapper.selectLogoById(postInfo.getCompanyId()));
                postInfoList.add(postInfo);
            }
        }
        return postInfoList;
    }

    @Override
    public List<PostInfo> getAllPost() {
        List<PostInfo> allPost = postInfoMapper.getAllPost();
        List<PostInfo> postInfos=new ArrayList<>();
        for(PostInfo postInfo:allPost){
            String companyName = companyInfoMapper.selectCompanyNameById(postInfo.getCompanyId());
            String logo=companyInfoMapper.selectLogoById(postInfo.getCompanyId());
            postInfo.setCompanyName(companyName);
            postInfo.setLogo(logo);
            postInfos.add(postInfo);
        }
        return postInfos;
    }

    @Override
    public PostInfo getPostInfoById(String postId) {
        PostInfo postInfo = postInfoMapper.getPostInfoById(postId);
        postInfo.setCompanyName(companyInfoMapper.selectCompanyNameById(postInfo.getCompanyId()));
        postInfo.setLogo(companyInfoMapper.selectLogoById(postInfo.getCompanyId()));
        return postInfo;
    }

    @Override
    public List<PostInfo> getSimilarType(String type) {
        List<PostInfo> postInfos=postInfoMapper.getAllPostByType(type);
        List<PostInfo> postInfoList=new ArrayList<>();
        for (PostInfo postInfo:postInfos){
            postInfo.setCompanyName(companyInfoMapper.selectCompanyNameById(postInfo.getCompanyId()));
            postInfo.setLogo(companyInfoMapper.selectLogoById(postInfo.getCompanyId()));
            postInfoList.add(postInfo);
        }
        Map map = new HashMap();
        List<PostInfo> result = new ArrayList<>();
        while (map.size() < 4) {
            int random = (int) (Math.random() * postInfoList.size());
            if (!map.containsKey(random)) {
                map.put(random, "");
                result.add(postInfoList.get(random));
            }
        }
        return result;
    }
}
