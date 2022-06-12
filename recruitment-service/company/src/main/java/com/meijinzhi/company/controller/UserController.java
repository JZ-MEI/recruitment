package com.meijinzhi.company.controller;

import com.meijinzhi.company.dao.CompanyInfoMapper;
import com.meijinzhi.company.entity.CompanyInfo;
import com.meijinzhi.company.entity.UserInfo;
import com.meijinzhi.company.service.CompanyInfoService;
import com.meijinzhi.company.util.SendMailUtil;
import com.meijinzhi.company.util.SendReturnMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    CompanyInfoService companyInfoService;
    @Resource
    private SendMailUtil sendMailUtil;
    @Autowired
    CompanyInfoMapper companyInfoMapper;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @RequestMapping(value = "/login")
    private SendReturnMessage login(CompanyInfo companyInfo) {
        SendReturnMessage msg = SendReturnMessage.instance();
        if (companyInfoService.LogIn(companyInfo)) {
            msg.setResponseCode("0000");
            msg.setMessage("密码正确");
            msg.setData(companyInfo);
        } else {
            msg.setResponseCode("9999");
            msg.setMessage("密码错误");
        }
        return msg;
    }

    @RequestMapping("/companyRegister")
    private SendReturnMessage register(CompanyInfo companyInfo, String verify) {
        SendReturnMessage msg = SendReturnMessage.instance();
        String correctVerify = String.valueOf(redisTemplate.opsForValue().get(companyInfo.getEmail()));
        try {
            if (companyInfo.getUsername() == null || companyInfo.getPassword() == null) {
                msg.setResponseCode("9999");
                msg.setMessage("用户名、密码、手机号不能为空");
            } else if (!verify.equals(correctVerify)) {
                msg.setResponseCode("9999");
                msg.setMessage("验证码错误");
            } else {
                if (companyInfoService.signIn(companyInfo)) {
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

    @RequestMapping("/forget")
    private SendReturnMessage forget(CompanyInfo companyInfo, String verify) {
        SendReturnMessage msg = SendReturnMessage.instance();
        String correctVerify = String.valueOf(redisTemplate.opsForValue().get(companyInfo.getEmail()));
        if (companyInfo.getUsername() != null && companyInfo.getPassword() != null) {
            if (verify.equals(correctVerify)) {
                if (companyInfoService.forget(companyInfo)) {
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
    @RequestMapping("updateCompanyInfo")
    public SendReturnMessage updateCompanyInfo(CompanyInfo companyInfo) {
        SendReturnMessage msg = SendReturnMessage.instance();
        try {
            companyInfoService.updateCompanyInfo(companyInfo);
            msg.setResponseCode("0000");
            msg.setMessage("success");
        } catch (Exception e) {
            msg.setResponseCode("9999");
            msg.setMessage("error");
        }
        return msg;
    }
    @RequestMapping("setLogo")
    public SendReturnMessage setLogo(MultipartFile file,String username) {
        SendReturnMessage msg = SendReturnMessage.instance();
        try {
            companyInfoService.updateLogo(file, username);
            msg.setResponseCode("0000");
            msg.setMessage("success");
        } catch (Exception e) {
            msg.setResponseCode("9999");
            msg.setMessage("error");
        }
        return msg;
    }
    @RequestMapping("setLicense")
    public SendReturnMessage setLicense(MultipartFile file,String username) {
        SendReturnMessage msg = SendReturnMessage.instance();
        try {
            companyInfoService.updateLicense(file, username);
            msg.setResponseCode("0000");
            msg.setMessage("success");
        } catch (Exception e) {
            msg.setResponseCode("9999");
            msg.setMessage("error");
        }
        return msg;
    }
}
