package com.meijinzhi.apply.controller;

import com.meijinzhi.apply.dao.CompanyInfoMapper;
import com.meijinzhi.apply.service.ApplyRecordService;
import com.meijinzhi.apply.service.CityService;
import com.meijinzhi.apply.service.PostInfoService;
import com.meijinzhi.apply.util.SendReturnMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
public class PostController {
    Logger logger = LoggerFactory.getLogger(PostController.class);
    @Autowired
    CityService cityService;
    @Autowired
    PostInfoService postInfoService;
    @Autowired
    CompanyInfoMapper companyInfoMapper;
    @Autowired
    ApplyRecordService applyRecordService;

    @RequestMapping("/hotCity")
    public SendReturnMessage getHotCity() {
        SendReturnMessage msg = SendReturnMessage.instance();
        try {
            msg.setData(cityService.getHotCity());
            msg.setResponseCode("0000");
            msg.setMessage("success");
        } catch (Exception e) {
            msg.setResponseCode("9999");
            msg.setMessage("error");
            logger.info("抛出异常", e);
        }
        return msg;
    }

    @RequestMapping("/province")
    public SendReturnMessage getProvince() {
        SendReturnMessage msg = SendReturnMessage.instance();
        try{
            msg.setResponseCode("0000");
            msg.setData(cityService.getAllProvincial());
            msg.setMessage("success");
        }catch (Exception e){
            msg.setResponseCode("9999");
            msg.setMessage("error");
            logger.info("抛出异常",e);
        }
        return msg;
    }
    @RequestMapping("/city")
    public SendReturnMessage getCity() {
        SendReturnMessage msg = SendReturnMessage.instance();
        try{
            msg.setResponseCode("0000");
            msg.setData(cityService.getAllCity());
            msg.setMessage("success");
        }catch (Exception e){
            msg.setResponseCode("9999");
            msg.setMessage("error");
            logger.info("抛出异常",e);
        }
        return msg;
    }
    @RequestMapping("/all")
    public SendReturnMessage getAllPost(){
        SendReturnMessage msg = SendReturnMessage.instance();
        try {
            msg.setData(postInfoService.getAllPost());
            msg.setMessage("success");
            msg.setResponseCode("0000");
        }catch (Exception e){
            msg.setResponseCode("9999");
            msg.setMessage("error");
            logger.info("抛出异常",e);
        }
        return msg;
    }
    @RequestMapping("/getPostInfo")
    public SendReturnMessage getPostInfo(String postId){
        SendReturnMessage msg = SendReturnMessage.instance();
        try {
            msg.setData(postInfoService.getPostInfoById(postId));
            msg.setMessage("success");
            msg.setResponseCode("0000");
        }catch (Exception e){
            msg.setResponseCode("9999");
            msg.setMessage("error");
            logger.info("抛出异常",e);
        }
        return msg;
    }
    @RequestMapping("/getSimilarPost")
    public SendReturnMessage getSimilarPost(String type){
        SendReturnMessage msg = SendReturnMessage.instance();
        try {
            msg.setData(postInfoService.getSimilarType(type));
            msg.setMessage("success");
            msg.setResponseCode("0000");
        }catch (Exception e){
            msg.setResponseCode("9999");
            msg.setMessage("error");
            logger.info("抛出异常",e);
        }
        return msg;
    }
    @RequestMapping("/getCompanyByUserName")
    public SendReturnMessage getCompanyByUserName(String companyId) {
        SendReturnMessage msg = SendReturnMessage.instance();
        try{
            msg.setData(companyInfoMapper.companyName(companyId));
            msg.setResponseCode("0000");
            msg.setMessage("success");
        }catch (Exception e){
            msg.setResponseCode("9999");
            msg.setMessage("error");
            e.printStackTrace();
        }
        return msg;
    }
    @RequestMapping("/sendApply")
    public SendReturnMessage sendApply(String username,String postId) {
        SendReturnMessage msg = SendReturnMessage.instance();
        try{
            applyRecordService.insertApply(username, postId);
//            msg.setData(companyInfoMapper.companyName(companyId));
            msg.setResponseCode("0000");
            msg.setMessage("success");
        }catch (Exception e){
            msg.setResponseCode("9999");
            msg.setMessage("error");
            e.printStackTrace();
        }
        return msg;
    }

}
