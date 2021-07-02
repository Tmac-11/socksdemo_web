package com.example.socksdemo.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.socksdemo.mapper.OrderMapper;
import com.example.socksdemo.mapper.SettingMapper;
import com.example.socksdemo.model.*;
import com.example.socksdemo.service.AmountService;
import com.example.socksdemo.service.DeviceService;
import com.example.socksdemo.service.ResourceService;
import com.example.socksdemo.service.UserService;
import com.example.socksdemo.service.impl.UserServiceImpl;
import com.example.socksdemo.utils.InvitationCodeUtil;
import com.example.socksdemo.utils.OrderUtil;
import com.example.socksdemo.utils.QRCodeUtil;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/indexController")
public class indexController {

    @Autowired
    DeviceService deviceService;
    @Autowired
    UserService userService;
    @Autowired
    ResourceService resourceService;
    @Autowired
    AmountService amountService;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    SettingMapper settingMapper;


    @Value("${logging.file}")
    private String filepath;

    private static Log log= LogFactory.getLog(indexController.class);

    @RequestMapping("/index")
    public String indexHtml(Model model){

        model.addAttribute("DeviceList",deviceService.DeviceInfo());
        model.addAttribute("UserList",userService.userFailure());
        model.addAttribute("today",LocalDate.now());
        return "index";
    }

    @RequestMapping("/adduserhtml")
    public String addUserHtml(){

        return "addUser";
    }

    @RequestMapping("/settingHtml")
    public String settingHtml(Model model){
        model.addAttribute("dayVar",settingMapper.querySetting());
        return "settingHtml";
    }


    @RequestMapping("/adduser")
    public String adduser(@ModelAttribute ResParam resParam, Model model){

        BaseResult baseResult= userService.ConfigUser(resParam);
        String info=null;

        if("success".equals(baseResult.getMsg())){
            info="添加操作成功！";
            model.addAttribute("user",baseResult.getData());
            UserInfo userInfo=(UserInfo)baseResult.getData();
//            try{
//                UserAmount userAmount=new UserAmount();
//                userAmount.setUser_id(userInfo.getUser_id());
//                userAmount.setUser_name(userInfo.getUser_name());
//                userAmount.setAmount_type("Insert Amount");
//                userAmount.setUser_amount(new BigDecimal(resParam.getAmount()));
//                userAmount.setAmount_creattime(LocalDate.now());
//                userAmount.setAmount_date(resParam.getStartdate()+"/"+resParam.getEnddate());
//                amountService.insertAmount(userAmount);
//
//            }catch (Exception e){
//                log.error("帐单存入失败："+e.getMessage());
//            }

            try{
                Map<String,String> map=QRCodeUtil.getQRCodeImage(userInfo.getKey_type(),String.valueOf(userInfo.getResource_port()),userInfo.getResource_domain_name(),userInfo.getUser_password(), 350, 350);
                String imgSrc=map.get("qrcode");
                String qrcode_info=map.get("qrcode_info");
                model.addAttribute("imgSrc",imgSrc);
                model.addAttribute("qrcode_info",qrcode_info);

            }catch (Exception e){
                    log.error("二维码生产失败："+e.getMessage());
            }

        }else{
             log.error(baseResult.getMsg());
             info="添加操作失败！";
        }

        model.addAttribute("info",info);
        return "msg";
    }

    @RequestMapping("/userInfoHtml")
    public String userInfoHtml(Model model){
       // PageInfo pageInfo=new PageInfo(nowpage,pagesize);

        model.addAttribute("UserList",userService.userlist());
        return "userInfo";
    }



    @RequestMapping("/userEdit")
    public String editHtml(@ModelAttribute UserInfo userinfo, Model model){
        model.addAttribute("user",userinfo);
        return "userEdit";
    }

    @RequestMapping(value = "/userResource" ,method = RequestMethod.POST)
    public String resource(ResUpdParam resUpdParam ,Model model){
//        UserInfo user =new UserInfo();
//        user.setUser_name(user_name);
//        user.setUser_id(Integer.valueOf(user_id));
//        user.setResource_id(Integer.valueOf(resource_id));
//        user.setResource_ip(newIp);
//        user.setResource_port(newPort);
//        user.setUser_password(newPassword);
//        user.setResource_state(resource_state);
//        user.setResource_type(new_resource_type);
//        user.setKey_type(new_key_type);
//        DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        user.setUser_creattime(LocalDateTime.parse(user_creattime,dtf));
       // System.out.println("进入upd..");
        BaseResult baseResult=resourceService.resourceUpd(resUpdParam);

        String info=null;

        if("success".equals(baseResult.getMsg())){
            info="更新操作成功！";
            model.addAttribute("user",baseResult.getData());
            UserInfo userInfo=(UserInfo)baseResult.getData();
            try{
               Map<String,String> map= QRCodeUtil.getQRCodeImage(userInfo.getKey_type(),String.valueOf(userInfo.getResource_port()),userInfo.getResource_domain_name(),userInfo.getUser_password(), 350, 350);
                String imgSrc=map.get("qrcode");
                String qrcode_info=map.get("qrcode_info");
                model.addAttribute("imgSrc",imgSrc);
                model.addAttribute("qrcode_info",qrcode_info);
            }catch (Exception e){
                log.error("二维码生产失败："+e.getMessage());
            }
        }else{
            info="更新操作失败！";
        }

        model.addAttribute("info",info);
        return "msg";
    }

    @RequestMapping("/userDel")
    public String userDel(String user_id,String resource_id,String resource_sate){
       // System.out.println(user_id+","+resource_id+","+resource_sate);
        userService.deleteUser(Integer.valueOf(user_id),Integer.valueOf(resource_id),resource_sate);
        return "redirect:/indexController/index";
    }

    @RequestMapping("/queryUser")
    public String queryUser(@ModelAttribute SearchVo searchVo, Model model){

        List<UserInfo> userlist= userService.queryUser(searchVo);
        model.addAttribute("UserList",userlist);
        return "userInfo";
    }

    @RequestMapping("/failUser")
    public String failUser(Integer user_id,Integer res_id){
       String info= userService.failUser( user_id, res_id);
        if("success".equals(info)){
            return "redirect:/indexController/userInfoHtml";
        }else{
            return "info";
        }
    }

    @RequestMapping("/updUser")
    public String updInfo(@ModelAttribute UserInfo userinfo,String amount, Model model){
        String info=userService.updUser(userinfo);
        if("success".equals(info)){
            if(!("0.0".equals(amount))){
                try{
                    OrderDataVo orderDataVo=new OrderDataVo();
                    orderDataVo.setOrder_no(OrderUtil.getInstance().nextId());
                    orderDataVo.setOrder_email_name(userinfo.getUser_name());
                    orderDataVo.setOrder_price(new BigDecimal(amount));
                    orderDataVo.setOrder_coupons_name("admin");
                    orderDataVo.setOrder_time(new Date());
                    orderDataVo.setOrder_status("1");
                    orderDataVo.setOrder_good_type("month");
                    orderDataVo.setOrder_type("Upd");
                    orderDataVo.setOrder_validity("Y");
                    orderMapper.insertOrder(orderDataVo);
                }catch (Exception e){
                    log.error("订单状态存储数据库错误:"+e.getMessage());
                }
            }
            return "redirect:/indexController/userInfoHtml";
        }else{
            return "info";
        }


    }

    @RequestMapping("/userinfoDel")
    public String userinfoDel(String user_id,String resource_id,String resource_sate){
        // System.out.println(user_id+","+resource_id+","+resource_sate);
        userService.deleteUser(Integer.valueOf(user_id),Integer.valueOf(resource_id),resource_sate);
        return "redirect:/indexController/userInfoHtml";
    }


    @RequestMapping("/qrcode")
    public String qrcodeHtml(UserInfo userInfo, Model model){
        try{
            JSONObject jsonObject=JSONObject.parseObject(userInfo.getResource_json());
            String method=(String)jsonObject.get("method");
            Map<String,String> map= QRCodeUtil.getQRCodeImage(method,String.valueOf(userInfo.getResource_port()),userInfo.getResource_domain_name(),userInfo.getUser_password(), 350, 350);
            String imgSrc=map.get("qrcode");
            String qrcode_info=map.get("qrcode_info");
            model.addAttribute("imgSrc",imgSrc);
            model.addAttribute("qrcode_info",qrcode_info);
        }catch (Exception e){
            log.error("二维码生产失败："+e.getMessage());
        }

        model.addAttribute("userInfo",userInfo);
        return "qrcodeHtml";
    }


    @RequestMapping("/hisInfoHtml")
    public String hisInfoHtml(){
        return "hisInfo";
    }

    @RequestMapping("/queryHisInfo")
    @ResponseBody
    public String HisInfo(@ModelAttribute SearchVo searchVo){
        List<UserHisInfo> list =userService.queryHisInfo(searchVo);
        String data= JSON.toJSONString(list);
        return data;
    }

    @RequestMapping("/logHtml")
    public String logHtml(Model model){
        model.addAttribute("filepath",filepath);
        return "log";
    }


    @RequestMapping("/queryLogFile")
    @ResponseBody
    public String logTxt(@RequestBody String path){
        BufferedReader reader = null;
        StringBuffer sbf=null;
        try {
            File file = new File(path);

            sbf= new StringBuffer();
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append("<br/>"+tempStr);
            }
            reader.close();
           // System.out.println(sbf.toString());
            return sbf.toString();
        } catch (IOException e) {
            log.info("文件读取失败："+e.getMessage());
            return "load file fail:"+e.getMessage();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }


    @RequestMapping("/publishHtml")
    public String publishHtml(){
        return "publishHtml";
    }


    @RequestMapping("/updateSetting")
    public String updateSetting(Integer newDayVar){
        settingMapper.upd(newDayVar);
        return "redirect:/indexController/settingHtml";
    }


}
