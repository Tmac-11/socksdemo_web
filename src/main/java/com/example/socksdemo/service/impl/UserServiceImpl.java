package com.example.socksdemo.service.impl;

import com.example.socksdemo.mapper.*;
import com.example.socksdemo.model.*;
import com.example.socksdemo.service.ResourceService;
import com.example.socksdemo.service.RewardService;
import com.example.socksdemo.service.UserService;
import com.example.socksdemo.utils.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static com.example.socksdemo.utils.PasswordUtil.RandomPassWord;
@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    ResourceService resourceService;
    @Autowired
    DeviceMapper deviceMapper;
    @Autowired
    ResourceMapper resourceMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    HisInfoMapper hisInfoMapper;
    @Autowired
    OrderMapper orderMapper;

    @Autowired
    RewardService rewardService;


    @Value("${portRangeStart}")
    private int portStart;
    @Value("${portRangeEnd}")
    private int portEnd;
    @Value("${ipCount}")
    private int ipCount;
    @Value("${sendPort}")
    private int sendPort;



    private static Log log= LogFactory.getLog(UserServiceImpl.class);

    @Override
    public BaseResult ConfigUser(ResParam resParam) {
        BaseResult baseResult=new BaseResult();
       String password= PasswordUtil.RandomPassWord();
        String ip=null;
        String port=null;
        String domainName=null;
        //自动分配ip
       if(resParam.getSelect_type().equals("auto")){
           Map<String,String> map=RandomIp();
            ip= map.get("ip");
           port=map.get("port");
            domainName=map.get("domainName");
       }else{
           //手动指定ip
           Map<String,String> map=manualIp(resParam.getSelect_ip());
           ip= map.get("ip");
           port=map.get("port");
           domainName=map.get("domainName");
       }

       if(ip==null&&port==null){
           baseResult.setMsg("没有有效设备，请先添加有效设备");
           return baseResult;
       }
        //String port="6001";
      // System.out.println("随机ip:"+ip+"--随机端口:"+port);
        String msg=null;
       String data=null;
        if("UDP".equals(resParam.getResource_type())){
            data="add: {\"server_port\":"+port+", \"password\":\""+password+"\"}";
            msg=UdpUtill.sendData(data,ip,sendPort);
        }else{
            resParam.setPort(port);
            resParam.setPasswd(password);
            resParam.setIp(ip);
            data=HttpUtil.jsonData(resParam);
            msg= HttpUtil.sendAddData(ip,data);

        }
        if("fail".equals(msg)){
        baseResult.setMsg(resParam.getResource_type()+" fail");
             return baseResult;
        }
        UserVo userVo=new UserVo();
        ResourceVo resourceVo=new ResourceVo();
        try{

            resourceVo.setResource_ip(ip);
            resourceVo.setResource_port(port);
            resourceVo.setResource_domain_name(domainName);
            resourceVo.setResource_state("1");
            resourceVo.setResource_loadtime(LocalDateTime.now());
            resourceVo.setResource_type(resParam.getResource_type());
            //resourceVo.setKey_type(resParam.getKey_type());
            resourceVo.setResource_json(data);
            Integer resId= resourceService.insertResource(resourceVo);
            resourceVo.setResource_id(resId);

            userVo.setUser_name(resParam.getUsername());
            userVo.setUser_password(password);
            userVo.setResource_id(resId);
//            userVo.setStartdate(LocalDate.now());
//            userVo.setEnddate(LocalDate.now().plusMonths(1));
            userVo.setStartdate(resParam.getStartdate());
            userVo.setEnddate(resParam.getEnddate());
            userVo.setUser_creattime(LocalDateTime.now());
            userVo.setUser_type(resParam.getUser_type());
            userVo.setUser_rights(Integer.valueOf(resParam.getRights()));
            Integer orderId=0;
            try{
                OrderDataVo orderDataVo=new OrderDataVo();
                orderDataVo.setOrder_no(OrderUtil.getInstance().nextId());
                orderDataVo.setOrder_email_name(resParam.getUsername());
                orderDataVo.setOrder_price(BigDecimal.valueOf(resParam.getAmount()));
                orderDataVo.setOrder_coupons_name("admin");
                orderDataVo.setOrder_time(new Date());
                orderDataVo.setOrder_status("1");
                orderDataVo.setOrder_good_type(resParam.getOrder_good_type());
                orderDataVo.setOrder_type("Add");
                orderDataVo.setOrder_validity("Y");
                orderMapper.insertOrder(orderDataVo);
                orderId=orderDataVo.getOrder_id();
                userVo.setUser_code(InvitationCodeUtil.toSerialCode(Long.valueOf(orderId)));
            }catch (Exception e){
                log.error("订单状态存储数据库错误:"+e.getMessage());
            }

           Integer user_id= insertUser(userVo);
            userVo.setUser_id(user_id);

        }catch (Exception e){
            log.error("resource表或user_socks表数据库插入失败"+e.getMessage());
            baseResult.setMsg("fail");
            return baseResult;
        }

        UserInfo userInfo=new UserInfo();
        userInfo.setUser_id(userVo.getUser_id());
        userInfo.setUser_name(userVo.getUser_name());
        userInfo.setUser_password(userVo.getUser_password());
        userInfo.setResource_ip(resourceVo.getResource_ip());
        userInfo.setResource_port(resourceVo.getResource_port());
        userInfo.setKey_type(resParam.getKey_type());
        userInfo.setResource_domain_name(resourceVo.getResource_domain_name());
        userInfo.setUser_code(userVo.getUser_code());
        userInfo.setUser_rights(userVo.getUser_rights());


        baseResult.setMsg("success");
        baseResult.setData(userInfo);

        //增加推荐码，给其他用户添加时间
        if(resParam.getReward_user_code()!=null && resParam.getReward_user_code()!=""){
            rewardService.addUseTime(resParam.getReward_user_code().trim());
        }

        try{
            UserHisInfo his=new UserHisInfo();
            his.setUser_id(userVo.getUser_id());
            his.setUser_name(userVo.getUser_name());
            his.setUser_password(userVo.getUser_password());
            his.setStartdate(userVo.getStartdate());
            his.setEnddate(userVo.getEnddate());
            his.setUser_creattime(userVo.getUser_creattime());
            his.setResource_id(resourceVo.getResource_id());
            his.setResource_ip(resourceVo.getResource_ip());
            his.setResource_port(resourceVo.getResource_port());
            his.setResource_loadtime(resourceVo.getResource_loadtime());
            his.setRemark_type("Insert User"+resourceVo.getResource_type()+"_type");
            hisInfoMapper.insertInfo(his);
        }catch (Exception e){
            log.error("his_ifo表数据库插入失败"+e.getMessage());
        }

        return baseResult;
    }

    /**
     * 随机分配ip+端口
     * @return
     */
    private Map<String,String> RandomIp(){
        Map<String,String> map=new HashMap<String,String>();
        try {
            List<DeviceVo> devicelist = deviceMapper.devicelist();
            if(devicelist.size()==0){
                return map;
            }
            Random random = new Random();
            int numIp = random.nextInt(devicelist.size());
            DeviceVo deviceVo = devicelist.get(numIp);
            String ip = deviceVo.getDevice_ip();
            String domain_name=deviceVo.getDevice_domain_name();
            List<ResourceVo> resourceList = resourceMapper.ResourceCount(ip);
            //假如资源表有启用
            if (resourceList != null && resourceList.size() != 0) {
                if (resourceList.size() > ipCount) {
                    RandomIp();
                } else {
                    int numPort = random.nextInt(portEnd) + portStart;
                    for(ResourceVo resourceVo :resourceList){
                        if(numPort!=Integer.parseInt(resourceVo.getResource_port())){
                            continue;
                        }else{
                            RandomIp();
                        }
                    }

                    map.put("ip", ip);
                    map.put("domainName",domain_name);
                    map.put("port", String.valueOf(numPort));
                }

            } else {
                //资源表没有启用，直接用此ip
                int numPort = random.nextInt(portEnd) + portStart;
                map.put("ip", ip);
                map.put("domainName",domain_name);
                map.put("port", String.valueOf(numPort));
            }

        }catch (Exception e){

            log.error("随机分配ip，端口发生错误"+e.getMessage());
        }
        return map;
    }


    @Override
    public  List<UserInfo> userlist() {

       return  userMapper.userInfo();

    }

    @Override
    public List<UserInfo> userFailure() {
        return userMapper.userFailure();
    }


    @Override
    public void deleteUser(Integer userid, Integer resid,String resource_sate) {
        String err=null;
        UserInfo userInfo=userMapper.queryByid(userid);
                if("0".equals(resource_sate)) {
                    try {
                        userMapper.delUser(userid);
                        resourceMapper.delResource(resid);
                    }catch (Exception e){
                        log.error("删除用户错误"+e.getMessage());
                    }
                }else{
                    try {
                        String data=null;
                        String msg=null;
                      ResourceVo resourceVo=  resourceMapper.ResourceByid(resid);
                      if("HTTP".equals(resourceVo.getResource_type())){
                          msg=HttpUtil.sendDelData(resourceVo.getResource_ip(),resourceVo.getResource_port());
                      }else{
                          data="remove: {\"server_port\":"+resourceVo.getResource_port()+"}";
                          msg=UdpUtill.sendData( data, resourceVo.getResource_ip(),sendPort);
                      }

                      if("fail".equals(msg)){
                          err=resourceVo.getResource_type()+"资源消除错误，";
                      }
                        userMapper.delUser(userid);
                        resourceMapper.delResource(resid);
                    }catch (Exception e){
                        log.error(err+"删除用户错误："+e.getMessage());
                    }


                }


        try{
            UserHisInfo his=new UserHisInfo();
            his.setUser_id(userInfo.getUser_id());
            his.setUser_name(userInfo.getUser_name());
            his.setUser_password(userInfo.getUser_password());
            his.setStartdate(userInfo.getStartdate());
            his.setEnddate(userInfo.getEnddate());
            his.setUser_creattime(userInfo.getUser_creattime());
            his.setResource_id(userInfo.getResource_id());
            his.setResource_ip(userInfo.getResource_ip());
            his.setResource_port(userInfo.getResource_port());
            his.setResource_loadtime(userInfo.getResource_loadtime());
            his.setRemark_type("delete User");
            hisInfoMapper.insertInfo(his);
        }catch (Exception e){
            log.error("his_ifo表数据库插入失败"+e.getMessage());
        }

    }

    @Override
    public List<UserInfo> queryUser(SearchVo searchVo) {

        return userMapper.queryUser(searchVo);
    }

    @Override
    public String failUser(Integer user_id,Integer res_id) {
        UserInfo userInfo = userMapper.queryByid(user_id);
        try {
            String msg=null;
            if("HTTP".equals(userInfo.getResource_type())){

               msg= HttpUtil.sendDelData(userInfo.getResource_ip(),userInfo.getResource_port());
            }else{
                String data="remove: {\"server_port\":"+userInfo.getResource_port()+"}";
                msg=  UdpUtill.sendData( data, userInfo.getResource_ip(),sendPort);
            }

            if("fail".equals(msg)){
                return userInfo.getResource_type()+"remove resource fail";
            }
            userInfo.setEnddate(LocalDate.now());
            userInfo.setResource_state("0");
            userMapper.updUser(userInfo);
            resourceMapper.updResource(userInfo);
        }catch (Exception e){
            log.error("用户失效错误："+e.getMessage());
            return "fail";
        }

        try{
            UserHisInfo his=new UserHisInfo();
            his.setUser_id(userInfo.getUser_id());
            his.setUser_name(userInfo.getUser_name());
            his.setUser_password(userInfo.getUser_password());
            his.setStartdate(userInfo.getStartdate());
            his.setEnddate(userInfo.getEnddate());
            his.setUser_creattime(userInfo.getUser_creattime());
            his.setResource_id(userInfo.getResource_id());
            his.setResource_ip(userInfo.getResource_ip());
            his.setResource_port(userInfo.getResource_port());
            his.setResource_loadtime(userInfo.getResource_loadtime());
            his.setRemark_type("fail User");
            hisInfoMapper.insertInfo(his);
        }catch (Exception e){
            log.error("his_ifo表数据库插入失败"+e.getMessage());
        }

        return "success";
    }


    @Override
    public void failUserList(List<UserInfo> list) {
        try {
             for (UserInfo userInfo:list){
                 String msg=null;
                 if("HTTP".equals(userInfo.getResource_type())){

                    msg= HttpUtil.sendDelData(userInfo.getResource_ip(),userInfo.getResource_port());
                 }else{
                     String data="remove: {\"server_port\":"+userInfo.getResource_port()+"}";
                     msg=UdpUtill.sendData( data, userInfo.getResource_ip(),sendPort);
                 }

                 if("fail".equals(msg)){
                     log.error(userInfo.getResource_type()+"remove resource fail list");
                 }else{
                     userInfo.setEnddate(LocalDate.now());
                     userInfo.setResource_state("0");
                     userMapper.updUser(userInfo);
                     resourceMapper.updResource(userInfo);

                     UserHisInfo his=new UserHisInfo();
                     his.setUser_id(userInfo.getUser_id());
                     his.setUser_name(userInfo.getUser_name());
                     his.setUser_password(userInfo.getUser_password());
                     his.setStartdate(userInfo.getStartdate());
                     his.setEnddate(userInfo.getEnddate());
                     his.setUser_creattime(userInfo.getUser_creattime());
                     his.setResource_id(userInfo.getResource_id());
                     his.setResource_ip(userInfo.getResource_ip());
                     his.setResource_port(userInfo.getResource_port());
                     his.setResource_loadtime(userInfo.getResource_loadtime());
                     his.setRemark_type("fail UserList");
                     hisInfoMapper.insertInfo(his);
                 }

             }

        }catch (Exception e){
            log.error("用户list失效||his_info insert错误："+e.getMessage());
        }

    }


    @Override
    public List<UserInfo> userTips(SearchVo searchVo) {
        return userMapper.userTips(searchVo);
    }

    @Override
    public Integer insertUser(UserVo userVo) {
     try{
         userMapper.addUser(userVo);
     }catch (Exception e){
         log.error("添加用户表错误："+e.getMessage());
     }

        return userVo.getUser_id();
    }


    @Override
    public String updUser(UserInfo userinfo) {
        ResourceVo vo=resourceMapper.ResourceByid(userinfo.getResource_id());
        if("1".equals(userinfo.getResource_state())){
            try{
                userMapper.updUser(userinfo);
            }catch (Exception e){
                log.error("用户信息更新失败："+e.getMessage());
                return "fail";
            }
        }else{
            String msg=null;
            String data=null;
            String ip=userinfo.getResource_ip();
            String port=userinfo.getResource_port();
            String password=userinfo.getUser_password();
            if("HTTP".equals(userinfo.getResource_type())){
                data=userinfo.getResource_json();
                msg= HttpUtil.sendAddData(userinfo.getResource_ip(),data);

            }else{
                data="add: {\"server_port\":"+port+", \"password\":\""+password+"\"}";
                msg=UdpUtill.sendData(data,ip,sendPort);
            }

            if("fail".equals(msg)){
                log.error(userinfo.getResource_type()+"重新申请失败：");
                return userinfo.getResource_type()+"fail resource";
            }else{

                try{

                    userinfo.setResource_state("1");
                    resourceMapper.updResource(userinfo);
                    userMapper.updUser(userinfo);

                }catch (Exception e){
                    log.error("用户信息更新失败："+e.getMessage());
                    return "fail upd";
                }
            }

        }


        try{

            UserHisInfo his=new UserHisInfo();
            his.setUser_id(userinfo.getUser_id());
            his.setUser_name(userinfo.getUser_name());
            his.setUser_password(userinfo.getUser_password());
            his.setStartdate(userinfo.getStartdate());
            his.setEnddate(userinfo.getEnddate());
            his.setUser_creattime(userinfo.getUser_creattime());
            his.setResource_id(userinfo.getResource_id());
            his.setResource_ip(userinfo.getResource_ip());
            his.setResource_port(userinfo.getResource_port());
            his.setResource_loadtime(vo.getResource_loadtime());
            his.setRemark_type("upd User Info");
            hisInfoMapper.insertInfo(his);
        }catch (Exception e){
            log.error("his_ifo表数据库插入失败"+e.getMessage());
        }


        return "success";

    }


    @Override
    public  List<UserHisInfo>  queryHisInfo(SearchVo searchVo) {

        return hisInfoMapper.hisInfo(searchVo);
    }


    /**
     * 手动分配ip+端口
     * @return
     */
    private Map<String,String> manualIp(String manualIp){
        Map<String,String> map=new HashMap<String,String>();
        try {
            String str[]=manualIp.split("/");
            String ip = str[0];
            String domain_name= str[1];
            Random random = new Random();
            List<ResourceVo> resourceList = resourceMapper.ResourceCount(ip);
            //假如资源表有启用
            if (resourceList != null && resourceList.size() != 0) {
                    int numPort = random.nextInt(portEnd) + portStart;
                    for(ResourceVo resourceVo :resourceList){
                        if(numPort!=Integer.parseInt(resourceVo.getResource_port())){
                            continue;
                        }else{
                            manualIp(manualIp);
                        }
                    }
                    map.put("ip", ip);
                    map.put("domainName",domain_name);
                    map.put("port", String.valueOf(numPort));

            } else {
                //资源表没有启用，直接用此ip
                int numPort = random.nextInt(portEnd) + portStart;
                map.put("ip", ip);
                map.put("domainName",domain_name);
                map.put("port", String.valueOf(numPort));
            }

        }catch (Exception e){

            log.error("手动分配ip，端口发生错误"+e.getMessage());
        }
        return map;
    }
}


