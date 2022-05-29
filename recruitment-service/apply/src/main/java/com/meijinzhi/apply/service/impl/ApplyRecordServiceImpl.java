package com.meijinzhi.apply.service.impl;

import com.meijinzhi.apply.dao.ApplyRecordMapper;
import com.meijinzhi.apply.dao.UserInfoMapper;
import com.meijinzhi.apply.entity.ApplyRecord;
import com.meijinzhi.apply.entity.UserInfo;
import com.meijinzhi.apply.service.ApplyRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ApplyRecordServiceImpl implements ApplyRecordService {
    @Autowired
    ApplyRecordMapper applyRecordMapper;
    @Autowired
    UserInfoMapper userInfoMapper;

    @Override
    public void insertApply(String username,String postId) {
        long userId = userInfoMapper.selectByUsername(username).getId();
        ApplyRecord applyRecord=new ApplyRecord();
        applyRecord.setUserId(String.valueOf(userId));
        applyRecord.setPostId(postId);
//        applyRecord.setId(null);
        applyRecordMapper.insertApplyRecord(applyRecord);
    }
}
