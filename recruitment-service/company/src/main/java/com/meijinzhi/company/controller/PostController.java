package com.meijinzhi.company.controller;

import com.meijinzhi.company.entity.PostInfo;
import com.meijinzhi.company.service.PostInfoService;
import com.meijinzhi.company.util.SendReturnMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
public class PostController {
    Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    PostInfoService postInfoService;
    @RequestMapping("/myPost")
    public SendReturnMessage myPost(String username,String keyWord,int page,int limit){
        SendReturnMessage msg= SendReturnMessage.instance();
        try{
            msg.setResponseCode("0");
            msg.setMessage("success");
            msg.setData(postInfoService.getMyPost(username,keyWord,page,limit));
            msg.setCount(postInfoService.getCount(username, keyWord));
        }catch (Exception e){
            msg.setResponseCode("9999");
            msg.setMessage("error");
            logger.info("抛出异常",e);
        }
        return msg;
    }
    @RequestMapping("/updatePost")
    public SendReturnMessage updatePost( PostInfo postInfo){
        SendReturnMessage msg= SendReturnMessage.instance();
        try{
            postInfoService.updatePost(postInfo);
            msg.setResponseCode("0");
            msg.setMessage("success");
        }catch (Exception e){
            msg.setResponseCode("9999");
            msg.setMessage("error");
            logger.info("抛出异常",e);
        }
        return msg;
    }
    @RequestMapping("/receiveResume")
    public SendReturnMessage receiveResume( String username ){
        SendReturnMessage msg= SendReturnMessage.instance();
        try{
            msg.setData(postInfoService.getApply(username));
            msg.setResponseCode("0");
            msg.setMessage("success");
        }catch (Exception e){
            msg.setResponseCode("9999");
            msg.setMessage("error");
            logger.info("抛出异常",e);
        }
        return msg;
    }
}
