package com.meijinzhi.company.service;

import com.meijinzhi.company.entity.PostInfo;
import com.meijinzhi.company.entity.ResumeInfo;

import java.util.List;
import java.util.Map;

public interface PostInfoService {
    List<PostInfo> getMyPost(String username, String keyWord, int page, int limit);

    int getCount(String username, String keyWord);

    void updatePost(PostInfo postInfo);

    List<ResumeInfo> getApply(String username);
}
