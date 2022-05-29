package com.meijinzhi.apply.service.impl;

import com.meijinzhi.apply.dao.BigDecimalMapper;
import com.meijinzhi.apply.dao.CompanyInfoMapper;
import com.meijinzhi.apply.dao.PostInfoMapper;
import com.meijinzhi.apply.entity.BigDecimal;
import com.meijinzhi.apply.entity.PostInfo;
import com.meijinzhi.apply.service.BigDecimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BigDecimalServiceImpl implements BigDecimalService {
    @Autowired
    BigDecimalMapper bigDecimalMapper;
    @Autowired
    PostInfoMapper postInfoMapper;
    @Autowired
    CompanyInfoMapper companyInfoMapper;
    @Override
    public void insertResult(String username, String result) {
        Date now = new Date();
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        BigDecimal bigDecimal = new BigDecimal();
        bigDecimal.setUsername(username);
        bigDecimal.setResult(result);
        bigDecimal.setRecordTime(df.format(now));
        bigDecimalMapper.insert(bigDecimal);
    }
    @Override
    public List<PostInfo> getBigDecimal(String username) {
        List<BigDecimal> getResult = bigDecimalMapper.getByUsername(username);
        List<PostInfo> postInfos = new ArrayList<>();
        List<PostInfo> allPost = postInfoMapper.getAllPost();
        List<PostInfo> allPostList=new ArrayList<>();
        for (PostInfo postInfo:allPost){
            postInfo.setCompanyName(companyInfoMapper.selectCompanyNameById(postInfo.getCompanyId()));
            postInfo.setLogo(companyInfoMapper.selectLogoById(postInfo.getCompanyId()));
            allPostList.add(postInfo);
        }
        if (getResult.size() <= 5) {
            for (int i = 0; i < getResult.size(); i++) {
                List<PostInfo> postInfoList = bigDecimalMapper.getBigDecimal(getResult.get(i).getResult());
                List<PostInfo> postInfoListAdd=new ArrayList<>();
                for(PostInfo postInfo:postInfoList){
                    postInfo.setCompanyName(companyInfoMapper.selectCompanyNameById(postInfo.getCompanyId()));
                    postInfo.setLogo(companyInfoMapper.selectLogoById(postInfo.getCompanyId()));
                    postInfoListAdd.add(postInfo);
                }
                postInfos.addAll(postInfoListAdd);
            }
        } else {
            for (int i = 0; i <= 5; i++) {
                List<PostInfo> postInfoList = bigDecimalMapper.getBigDecimal(getResult.get(i).getResult());
                List<PostInfo> postInfoListAdd=new ArrayList<>();
                for(PostInfo postInfo:postInfoList){
                    postInfo.setCompanyName(companyInfoMapper.selectCompanyNameById(postInfo.getCompanyId()));
                    postInfo.setLogo(companyInfoMapper.selectLogoById(postInfo.getCompanyId()));
                    postInfoListAdd.add(postInfo);
                }
                postInfos.addAll(postInfoListAdd);
            }
        }
        List<PostInfo> deleteRepeat = new ArrayList<>();
        Set set = new HashSet();
        for (PostInfo postInfo : postInfos) {
            if (set.add(postInfo)) {
                deleteRepeat.add(postInfo);
            }
        }
        Map map = new HashMap();
        List<PostInfo> result = new ArrayList<>();
        if (deleteRepeat.size() <= 15) {
            while (map.size() < 15) {
                int random = (int) (Math.random() * allPostList.size());
                if (!map.containsKey(random)) {
                    map.put(random, "");
                    result.add(allPostList.get(random));
                }
            }
        } else {
            while (map.size() < 15) {
                int random = (int) (Math.random() * deleteRepeat.size());
                if (!map.containsKey(random)) {
                    map.put(random, "");
                    result.add(deleteRepeat.get(random));
                }
            }
        }
        return result;
    }
}
