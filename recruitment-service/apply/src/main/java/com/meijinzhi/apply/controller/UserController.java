package com.meijinzhi.apply.controller;


import com.meijinzhi.apply.entity.ResumeInfo;
import com.meijinzhi.apply.entity.UserInfo;
import com.meijinzhi.apply.service.UserInfoService;
import com.meijinzhi.apply.util.SendMailUtil;
import com.meijinzhi.apply.util.SendReturnMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController{
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserInfoService userInfoService;
    @Resource
    private SendMailUtil sendMailUtil;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    private static String userId;

    @RequestMapping("/clientRegister")
    @CrossOrigin
    private SendReturnMessage register(UserInfo userInfo, String verify) {
        SendReturnMessage msg = SendReturnMessage.instance();
        String correctVerify = String.valueOf(redisTemplate.opsForValue().get(userInfo.getEmail()));
        try {
            if (userInfo.getUsername() == null || userInfo.getPassword() == null) {
                msg.setResponseCode("9999");
                msg.setMessage("用户名、密码、手机号不能为空");
            } else if (!verify.equals(correctVerify)) {
                msg.setResponseCode("9999");
                msg.setMessage("验证码错误");
            } else {
                if (userInfoService.signIn(userInfo)) {
                    msg.setResponseCode("0000");
                    msg.setMessage("注册成功");
                } else {
                    msg.setResponseCode("9999");
                    msg.setMessage("未知错误，请联系管理员处理");
                }
            }
        } catch (Exception e) {
            msg.setResponseCode("9999");
            msg.setMessage(String.valueOf(e));
            e.printStackTrace();
        }
        return msg;
    }

    @RequestMapping(value = "/login")
    @CrossOrigin
    private SendReturnMessage login(UserInfo userInfo) {
        SendReturnMessage msg = SendReturnMessage.instance();
        if (userInfoService.LogIn(userInfo)) {
            msg.setResponseCode("0000");
            msg.setMessage("密码正确");
            msg.setData(userInfo);
        } else {
            msg.setResponseCode("9999");
            msg.setMessage("密码错误");
        }
        return msg;
    }

    @RequestMapping("/forget")
    @CrossOrigin
    private SendReturnMessage forget(UserInfo userInfo, String verify) {
        SendReturnMessage msg = SendReturnMessage.instance();
        String correctVerify = String.valueOf(redisTemplate.opsForValue().get(userInfo.getEmail()));
        if (userInfo.getUsername() != null && userInfo.getPassword() != null) {
            if (verify.equals(correctVerify)) {
                if (userInfoService.forget(userInfo)) {
                    msg.setResponseCode("0000");
                    msg.setMessage("重置成功");
                } else {
                    msg.setResponseCode("9999");
                    msg.setMessage("用户名不存在或手机号错误");
                }
            }else{
                msg.setResponseCode("9999");
                msg.setMessage("验证码错误");
            }
        } else {
            msg.setResponseCode("9999");
            msg.setMessage("用户名或密码不能为空");
        }
        return msg;
    }

    @RequestMapping("/sendVerify")
    @CrossOrigin
    public SendReturnMessage sendVerify(UserInfo userInfo) {
        SendReturnMessage msg = SendReturnMessage.instance();
        try {
            String verify = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
            StringBuilder sb = new StringBuilder();
            sb.append("您的验证码是：").append(verify).append("为保证您的财产安全，请勿将验证码告诉其他人");
            String emailMessage = sb.toString();
            sendMailUtil.sendMail("1749092177@qq.com", userInfo.getEmail(), "网上商城验证码", emailMessage);
            redisTemplate.opsForValue().set(userInfo.getEmail(), verify);
            msg.setResponseCode("0000");
            msg.setMessage("发送成功");
        } catch (Exception e) {
            msg.setResponseCode("9999");
            msg.setMessage("发送失败");
        }
        return msg;
    }
    @RequestMapping("/resume")
    public SendReturnMessage saveResume(ResumeInfo resumeInfo,String[] companyName,String[] startTime,String[] endTime,String[] workDescribe,String username) {
        SendReturnMessage msg = SendReturnMessage.instance();
        try{
            userInfoService.setResume(resumeInfo, companyName, startTime, endTime, workDescribe, username);
            msg.setResponseCode("0000");
            msg.setMessage("success");
        }catch (Exception e){
            msg.setResponseCode("9999");
            msg.setMessage("error");
            logger.info("抛出异常",e);
        }
        return msg;
    }
    @RequestMapping("/photo")
    public SendReturnMessage photo(MultipartFile file){
        SendReturnMessage msg = SendReturnMessage.instance();
        try{
            userInfoService.updatePhoto(file);
            msg.setResponseCode("0000");
            msg.setMessage("success");
        }catch (Exception e){
            msg.setResponseCode("9999");
            msg.setMessage("error");
            logger.info("抛出异常",e);
        }
        return msg;
    }
//    @RequestMapping("/resume")
//    public SendReturnMessage saveResume(String fields){
//        SendReturnMessage msg = SendReturnMessage.instance();
////        logger.info(String.valueOf(jsonObject));
//        return msg;
//    }
}
