package com.meijinzhi.apply.dao;

import com.meijinzhi.apply.entity.PostInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostInfoMapper {
    List<PostInfo> getAllPostByType(String type);
    List<PostInfo> getAllPost();
    List<PostInfo> getPostByKeyWord(String keyWord);
    PostInfo getPostInfoById(String postId);
}
