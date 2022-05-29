package com.meijinzhi.apply.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/websocket")
public class WebSocketController {
    Logger logger = LoggerFactory.getLogger(WebSocketController.class);
    @RequestMapping("/{name}")
    public String webSocket(@PathVariable String name, Model model) {
        try {
            logger.info("跳转到websocket的页面上");
            model.addAttribute("username", name);
            return "websocket";
        } catch (Exception e) {
            logger.info("跳转到websocket的页面上发生异常，异常信息是：" + e.getMessage());
            return "error";
        }
    }
}
