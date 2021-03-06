package com.meijinzhi.company.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meijinzhi.company.entity.PostInfo;
import com.meijinzhi.company.entity.ResumeInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostInfoMapper extends BaseMapper<PostInfo> {
    List<PostInfo> getMyPost(@Param("id") long id, @Param("keyWord") String keyWord, @Param("start") int start, @Param("limit") int limit);

    List<PostInfo> getPostCount(@Param("id") long id, @Param("keyWord") String keyWord);

    List<PostInfo> getAllPost(@Param("isAudit") String isAudit, @Param("start") int start, @Param("limit") int limit, @Param("keyWord") String keyWord);

    List<PostInfo> getAllPostCount(@Param("isAudit") String isAudit, @Param("keyWord") String keyWord);

    PostInfo getPostById(String postId);

    int insertPost(PostInfo postInfo);
}
