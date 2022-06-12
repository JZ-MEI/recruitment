package com.meijinzhi.apply.service;

import com.meijinzhi.apply.entity.ApplyRecord;
import com.meijinzhi.apply.entity.PostInfo;

import java.util.List;

public interface ApplyRecordService {
    void insertApply(String username,String postId);
    List<PostInfo> getMyDelivery(String username, int page, int limit);
    int getRecordCount(String username);
}
