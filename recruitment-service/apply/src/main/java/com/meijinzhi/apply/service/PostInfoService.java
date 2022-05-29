package com.meijinzhi.apply.service;

import com.meijinzhi.apply.entity.PostInfo;

import java.util.List;

public interface PostInfoService {
    List<PostInfo> getPostByType(String keyWord,String type);
    List<PostInfo> getAllPost();
    PostInfo getPostInfoById(String postId);
    List<PostInfo> getSimilarType(String type);
}
