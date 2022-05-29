package com.meijinzhi.company.util;


import lombok.Data;

import java.util.Map;

@Data
public class SendReturnMessage {
    private String responseCode;
    private String message;
    private Object data;
    private int count;


    public static SendReturnMessage instance(){
        try{
            return SendReturnMessage.class.newInstance();
        }catch (InstantiationException e){
            e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }
        return new SendReturnMessage();
    }
    public void addMessage(Map<String,Object> messageMap){
        if (null!=messageMap&&!messageMap.isEmpty()){
            if (messageMap.containsKey("code")&&null!=messageMap.get("responseCode")){
                this.setResponseCode(String.valueOf(messageMap.get("responseCode")));
            }
            if (messageMap.containsKey("message")&&null!=messageMap.get("message")){
                this.setMessage(String.valueOf(messageMap.get("message")));
            }
        }
    }
}
