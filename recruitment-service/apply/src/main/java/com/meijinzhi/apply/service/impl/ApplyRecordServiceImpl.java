package com.meijinzhi.apply.service.impl;

import com.meijinzhi.apply.dao.ApplyRecordMapper;
import com.meijinzhi.apply.dao.PostInfoMapper;
import com.meijinzhi.apply.dao.UserInfoMapper;
import com.meijinzhi.apply.entity.ApplyRecord;
import com.meijinzhi.apply.entity.PostInfo;
import com.meijinzhi.apply.entity.UserInfo;
import com.meijinzhi.apply.service.ApplyRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ApplyRecordServiceImpl implements ApplyRecordService {
    @Autowired
    ApplyRecordMapper applyRecordMapper;
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    PostInfoMapper postInfoMapper;


    @Override
    public void insertApply(String username, String postId) {
        long userId = userInfoMapper.selectByUsername(username).getId();
        PostInfo postInfo = postInfoMapper.getPostInfoById(postId);
        ApplyRecord applyRecord = new ApplyRecord();
        applyRecord.setUserId(String.valueOf(userId));
        applyRecord.setPostId(postId);
        applyRecord.setCompanyId(String.valueOf(postInfo.getCompanyId()));
        applyRecordMapper.insertApplyRecord(applyRecord);
    }

    @Override
    public List<PostInfo> getMyDelivery(String username, int page, int limit) {
        int start = 0;
        if (page != 1) {
            start = (page - 1) * limit;
        }
        UserInfo userInfo = userInfoMapper.selectByUsername(username);
        String userId=String.valueOf(userInfo.getId());
        List<ApplyRecord> applyRecords = applyRecordMapper.getRecordByUserId(userId, start, limit);
        List<PostInfo> postInfoList = new ArrayList<>();
        for (ApplyRecord applyRecord:applyRecords){
            PostInfo postInfo = postInfoMapper.getPostInfoById(applyRecord.getPostId());
            postInfoList.add(postInfo);
        }
        return postInfoList;
    }

    @Override
    public int getRecordCount(String username) {
        UserInfo userInfo = userInfoMapper.selectByUsername(username);
        String userId=String.valueOf(userInfo.getId());
        return applyRecordMapper.getRecordCount(userId).size();
    }
}
