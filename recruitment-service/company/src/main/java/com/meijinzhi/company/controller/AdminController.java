package com.meijinzhi.company.controller;

import com.meijinzhi.company.entity.AdminInfo;
import com.meijinzhi.company.entity.CompanyInfo;
import com.meijinzhi.company.entity.UserInfo;
import com.meijinzhi.company.service.AdminInfoService;
import com.meijinzhi.company.util.SendMailUtil;
import com.meijinzhi.company.util.SendReturnMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin")
public class AdminController {
    Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    AdminInfoService adminInfoService;
    @Resource
    private SendMailUtil sendMailUtil;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @RequestMapping(value = "/login")
    private SendReturnMessage login(AdminInfo adminInfo) {
        SendReturnMessage msg = SendReturnMessage.instance();
        if (adminInfoService.LogIn(adminInfo)) {
            msg.setResponseCode("0000");
            msg.setMessage("密码正确");
            msg.setData(adminInfoService.getAdmin(adminInfo.getAdminName()));
        } else {
            msg.setResponseCode("9999");
            msg.setMessage("密码错误");
        }
        return msg;
    }

    @RequestMapping("/adminRegister")
    private SendReturnMessage register(AdminInfo adminInfo, String verify) {
        SendReturnMessage msg = SendReturnMessage.instance();
        String correctVerify = String.valueOf(redisTemplate.opsForValue().get(adminInfo.getEmail()));
        try {
            if (adminInfo.getAdminName() == null || adminInfo.getPassword() == null) {
                msg.setResponseCode("9999");
                msg.setMessage("用户名、密码、手机号不能为空");
            } else if (!verify.equals(correctVerify)) {
                msg.setResponseCode("9999");
                msg.setMessage("验证码错误");
            } else {
                if (adminInfoService.signIn(adminInfo)) {
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
//
    @RequestMapping("/forget")
    private SendReturnMessage forget(AdminInfo adminInfo, String verify) {
        SendReturnMessage msg = SendReturnMessage.instance();
        String correctVerify = String.valueOf(redisTemplate.opsForValue().get(adminInfo.getEmail()));
        if (adminInfo.getAdminName() != null && adminInfo.getPassword() != null) {
            if (verify.equals(correctVerify)) {
                if (adminInfoService.forget(adminInfo)) {
                    msg.setResponseCode("0000");
                    msg.setMessage("重置成功");
                } else {
                    msg.setResponseCode("9999");
                    msg.setMessage("用户名不存在或手机号错误");
                }
            } else {
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
    public SendReturnMessage sendVerify(UserInfo userInfo) {
        SendReturnMessage msg = SendReturnMessage.instance();
        try {
            String verify = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
            StringBuilder sb = new StringBuilder();
            sb.append("您的验证码是：").append(verify).append("为保证您的财产安全，请勿将验证码告诉其他人");
            String emailMessage = sb.toString();
            sendMailUtil.sendMail("1749092177@qq.com", userInfo.getEmail(), "煤气罐招聘验证码", emailMessage);
            redisTemplate.opsForValue().set(userInfo.getEmail(), verify);
            msg.setResponseCode("0000");
            msg.setMessage("发送成功");
        } catch (Exception e) {
            msg.setResponseCode("9999");
            msg.setMessage("发送失败");
        }
        return msg;
    }
    @RequestMapping("/getCompany")
    public SendReturnMessage getCompany(int page,int limit,String keyWord){
        SendReturnMessage msg = SendReturnMessage.instance();
        try {
            msg.setResponseCode("0000");
            msg.setMessage("success");
            msg.setData(adminInfoService.getCompanyInfo(page, limit,keyWord));
            msg.setCount(adminInfoService.getCompanyInfoCount(keyWord));
        }catch (Exception e){
            msg.setResponseCode("9999");
            msg.setMessage("error");
            e.printStackTrace();
        }
        return msg;
    }
    @RequestMapping("/auditPass")
    public SendReturnMessage auditPass(String username,long companyId){
        SendReturnMessage msg=SendReturnMessage.instance();
        try{
            adminInfoService.audit(username, companyId);
            msg.setResponseCode("0000");
            msg.setMessage("success");
        }catch (Exception e){
            msg.setResponseCode("9999");
            msg.setMessage("error");
            e.printStackTrace();
        }

        return msg;
    }
    @RequestMapping("/getPost")
    public SendReturnMessage getPost(String isAudit,int page,int limit,String keyWord){
        SendReturnMessage msg=SendReturnMessage.instance();
        try{
            msg.setResponseCode("0000");
            msg.setMessage("success");
            msg.setData(adminInfoService.getAllPost(isAudit,page, limit,keyWord));
            msg.setCount(adminInfoService.getAllPostCount(isAudit,keyWord));
        }catch (Exception e){
            msg.setResponseCode("9999");
            msg.setMessage("error");
            e.printStackTrace();
        }
        return msg;
    }
    @RequestMapping("/auditPost")
    public SendReturnMessage auditPost(String username,long postId) {
        SendReturnMessage msg = SendReturnMessage.instance();
        try {
            adminInfoService.auditPost(username, postId);
            msg.setResponseCode("0000");
            msg.setMessage("success");
        } catch (Exception e) {
            msg.setResponseCode("9999");
            msg.setMessage("error");
            e.printStackTrace();
        }
        return msg;
    }
    @RequestMapping("/getAdmin")
    public SendReturnMessage getAdmin(int page,int limit,String keyWord) {
        SendReturnMessage msg = SendReturnMessage.instance();
        try {
            msg.setData(adminInfoService.selectAdmin(page, limit, keyWord));
            msg.setCount(adminInfoService.getAdminCount(keyWord));
            msg.setResponseCode("0000");
            msg.setMessage("success");
        } catch (Exception e) {
            msg.setResponseCode("9999");
            msg.setMessage("error");
            e.printStackTrace();
        }
        return msg;
    }
    @RequestMapping("/adminPass")
    public SendReturnMessage adminPass(long adminId,String type) {
        SendReturnMessage msg = SendReturnMessage.instance();
        try {
            adminInfoService.adminAudit(adminId,type);
            msg.setResponseCode("0000");
            msg.setMessage("success");
        }catch (Exception e) {
            msg.setResponseCode("9999");
            msg.setMessage("error");
            e.printStackTrace();
        }
        return msg;
    }
    @RequestMapping("/upload")
    public SendReturnMessage upload(MultipartFile file,String index){
        SendReturnMessage msg = SendReturnMessage.instance();
        try {
            adminInfoService.upload(file,index);
            msg.setResponseCode("0000");
            msg.setMessage("success");
        }catch (Exception e) {
            msg.setResponseCode("9999");
            msg.setMessage("error");
            e.printStackTrace();
        }
        return msg;
    }
}
