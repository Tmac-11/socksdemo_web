package com.example.socksdemo.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.socksdemo.model.ResParam;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

public class HttpUtil {

    private static Log log= LogFactory.getLog(HttpUtil.class);

    public static  String  sendAddData(String ip,String data){
        log.info("开始http通信");
        String url="http://"+ip+":8080/v1/worker/ss/add-job";
   //使用Restemplate来发送HTTP请求
        RestTemplate restTemplate = new RestTemplate();
        //String data="{\"port\":"+port+",\"passwd\":"+password+",\"method\":"+key_type+"}";
        System.out.println(data);
        JSONObject jsonObject=JSONObject.parseObject(data);

        //设置请求header 为 APPLICATION_FORM_URLENCODED
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 请求体，包括请求数据 body 和 请求头 headers
        HttpEntity httpEntity = new HttpEntity(jsonObject,headers);


        try {
            //使用 exchange 发送请求，以String的类型接收返回的数据
            //ps，我请求的数据，其返回是一个json
            ResponseEntity<String> strbody = restTemplate.postForEntity(url,httpEntity,String.class);

            //解析返回的数据
           String msg=strbody.getBody().toString();
            log.info("结束http通信");
            log.info(msg);
            if(msg.contains("success")){
                return "success";
            }


        }catch (Exception e){
           log.error("http通信发生错误"+e.getMessage());
        }


        return "fail";
    }

    public static  String  sendDelData(String ip,String port){
        log.info("开始http通信");
        String url="http://"+ip+":8080/v1/worker/ss/del-job";
        //使用Restemplate来发送HTTP请求
        RestTemplate restTemplate = new RestTemplate();
        String data="{\"port\":"+port+"}";
        JSONObject jsonObject=JSONObject.parseObject(data);
        //设置请求header 为 APPLICATION_FORM_URLENCODED
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 请求体，包括请求数据 body 和 请求头 headers
        HttpEntity httpEntity = new HttpEntity(jsonObject,headers);
        try {
            //使用 exchange 发送请求，以String的类型接收返回的数据
            //ps，我请求的数据，其返回是一个json
            ResponseEntity<String> strbody = restTemplate.postForEntity(url,httpEntity,String.class);

            //解析返回的数据
            String msg=strbody.getBody().toString();
            log.info("结束http通信");
            log.info(msg);
            if(msg.contains("success")){
                return "success";
            }


        }catch (Exception e){
            log.error("http通信发生错误"+e.getMessage());
            log.info("失败数据ip:"+ip+"port:"+port);
        }

        return "fail";

    }


    public static String jsonData(ResParam resParam){
    String data="{\"port\":"+resParam.getPort()+",\"passwd\":\""+resParam.getPasswd()+"\",\"method\":\""+resParam.getKey_type()+"\",\"mode\":\""+resParam.getMode()+"\",\"timeout\":"+resParam.getTimeout()+"}";

//        if("ture".equals(resParam.getFast_open())){
//
//        }

        return data;
    }

    public static void main(String[] args) {

        String msg=sendDelData("154.220.3.169","52397");
       // String msg= sendAddData("38.143.19.31","");

            System.out.println(msg);

    }
}
