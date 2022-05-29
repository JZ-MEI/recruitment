package com.meijinzhi.apply.controller;

import com.meijinzhi.apply.service.BigDecimalService;
import com.meijinzhi.apply.service.CarouselImgService;
import com.meijinzhi.apply.service.PostInfoService;
import com.meijinzhi.apply.util.SendReturnMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/index")
public class IndexController {
    Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    CarouselImgService carouselImgService;
    @Autowired
    PostInfoService postInfoService;
    @Autowired
    BigDecimalService bigDecimalService;
    @RequestMapping("/carousel")
    public SendReturnMessage getCarousel(){
        SendReturnMessage msg = SendReturnMessage.instance();
        msg.setData(carouselImgService.getAllCarousel());
        return msg;
    }
    @RequestMapping("/post")
    public SendReturnMessage getPost(String keyWord, String type){
        SendReturnMessage msg = SendReturnMessage.instance();
        try{
            msg.setResponseCode("0000");
            msg.setData(postInfoService.getPostByType(keyWord,type));
        }catch(Exception e){
            msg.setResponseCode("9999");
            msg.setMessage("error");
            logger.info("抛出异常",e);
        }
        return msg;
    }
    @RequestMapping("/addDecimal")
    public SendReturnMessage addDecimal(String username, String result) {
        SendReturnMessage msg = SendReturnMessage.instance();
        try {
            bigDecimalService.insertResult(username, result);
            msg.setResponseCode("0000");
            msg.setMessage("success");
        } catch (Exception e) {
            e.printStackTrace();
            msg.setResponseCode("9999");
            msg.setMessage("error");
        }
        return msg;
    }
    @RequestMapping("/guess")
    public SendReturnMessage guessYouLike(String username) {
        SendReturnMessage msg = SendReturnMessage.instance();
        try {
            msg.setData(bigDecimalService.getBigDecimal(username));
            msg.setResponseCode("0000");
            msg.setMessage("success");
        } catch (Exception e) {
            e.printStackTrace();
            msg.setResponseCode("9999");
            msg.setMessage("error");
        }
        return msg;
    }
}
