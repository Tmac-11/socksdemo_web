package com.example.socksdemo.service.impl;

import com.example.socksdemo.mapper.DeviceMapper;
import com.example.socksdemo.mapper.HisInfoMapper;
import com.example.socksdemo.mapper.ResourceMapper;
import com.example.socksdemo.mapper.UserMapper;
import com.example.socksdemo.model.*;
import com.example.socksdemo.service.ResourceService;
import com.example.socksdemo.utils.HttpUtil;
import com.example.socksdemo.utils.UdpUtill;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
@Transactional
public class ResourceServiceImpl implements ResourceService{

    private static Log log= LogFactory.getLog(ResourceServiceImpl.class);
    @Autowired
    ResourceMapper resourceMapper;
    @Autowired
    DeviceMapper deviceMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    HisInfoMapper hisInfoMapper;
    @Value("${portRangeStart}")
    private int portStart;
    @Value("${portRangeEnd}")
    private int portEnd;
    @Value("${ipCount}")
    private int ipCount;
    @Value("${sendPort}")
    private int sendPort;

    @Override
    public Integer insertResource(ResourceVo resourceVo) {
        try{
            resourceMapper.insertResource(resourceVo);
        }catch (Exception e){

            log.error("资源表插入数据库失败："+e.getMessage());
        }

        return resourceVo.getResource_id();
    }

    @Override
    public Map<String, String> RandomIp() {
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
    public Map<String, String> apecifiedIp(String manualIp) {
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
                        apecifiedIp(manualIp);
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

    @Transactional
    @Override
    public BaseResult resourceUpd(ResUpdParam resUpdParam) {
        BaseResult baseResult=new BaseResult();
        UserInfo user =new UserInfo();
        user.setUser_name(resUpdParam.getUser_name());
        user.setUser_id(Integer.valueOf(resUpdParam.getUser_id()));
        user.setResource_id(Integer.valueOf(resUpdParam.getResource_id()));
        user.setResource_ip(resUpdParam.getNewIp());
        user.setResource_domain_name(resUpdParam.getNewDomainName());
        user.setResource_port(resUpdParam.getNewPort());
        user.setUser_password(resUpdParam.getNewPassword());
        user.setResource_state(resUpdParam.getResource_state());
        user.setResource_type(resUpdParam.getNew_resource_type());
        user.setKey_type(resUpdParam.getNew_key_type());
        user.setStartdate(resUpdParam.getNew_startdate());
        user.setEnddate(resUpdParam.getNew_enddate());
        user.setUser_type(resUpdParam.getUser_type());
        user.setUser_rights(resUpdParam.getUser_rights());
        user.setUser_code(resUpdParam.getUser_code());
        DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        user.setUser_creattime(LocalDateTime.parse(resUpdParam.getUser_creattime(),dtf));

        ResourceVo resourceVo=  resourceMapper.ResourceByid(Integer.parseInt(resUpdParam.getResource_id()));
        //强制失效
        if("1".equals(resUpdParam.getForced_state())){

            if("1".equals(resUpdParam.getResource_state())){

                user.setResource_state("0");
            }

            //非强制失效
        }else {

            if("1".equals(resUpdParam.getResource_state())){
                String msg=null;

                //状态没失效
                if("HTTP".equals(resourceVo.getResource_type())){
                    msg= HttpUtil.sendDelData(resourceVo.getResource_ip(),resourceVo.getResource_port());
                }else{
                    String data="remove: {\"server_port\":"+resourceVo.getResource_port()+"}";
                    msg=UdpUtill.sendData( data, resourceVo.getResource_ip(),sendPort);
                }
                if("fail".equals(msg)){
                    baseResult.setMsg(resUpdParam.getResource_type()+"resource delete fail");
                    return baseResult;
                }
                user.setResource_state("0");
            }
        }


        String msg=null;
        String data=null;
        if("HTTP".equals(resUpdParam.getNew_resource_type())){
                ResParam resParam=new ResParam();
                resParam.setPort(resUpdParam.getNewPort());
                resParam.setPasswd(resUpdParam.getNewPassword());
                resParam.setKey_type(resUpdParam.getNew_key_type());
                resParam.setMode(resUpdParam.getNew_mode());
                resParam.setTimeout(resUpdParam.getNew_timeout());
                data= HttpUtil.jsonData(resParam);
                msg=HttpUtil.sendAddData(resUpdParam.getNewIp(),data);
           // msg="success";
        }else{
           data="add: {\"server_port\":"+resUpdParam.getNewPort()+", \"password\":\""+resUpdParam.getNewPassword()+"\"}";
           msg= UdpUtill.sendData(data,resUpdParam.getNewIp(),sendPort);
        }

        if("fail".equals(msg)){
            baseResult.setMsg("resource add fail");
             return baseResult;
        }
        try {
            user.setResource_json(data);
//            user.setStartdate(LocalDate.now());
//            user.setEnddate(LocalDate.now().plusMonths(1));
            user.setResource_state("1");
            userMapper.updUser(user);
            resourceMapper.updResource(user);
        }catch (Exception e){
            log.error("资源更新失败："+e.getMessage());
            baseResult.setMsg("资源更新失败");
            return baseResult;
        }

        baseResult.setMsg("success");
        baseResult.setData(user);
        try{
            UserHisInfo his=new UserHisInfo();
            his.setUser_id(user.getUser_id());
            his.setUser_name(user.getUser_name());
            his.setUser_password(user.getUser_password());
            his.setStartdate(user.getStartdate());
            his.setEnddate(user.getEnddate());
            his.setUser_creattime(user.getUser_creattime());
            his.setResource_id(resourceVo.getResource_id());
            his.setResource_ip(resourceVo.getResource_ip());
            his.setResource_port(resourceVo.getResource_port());
            his.setResource_loadtime(resourceVo.getResource_loadtime());
            his.setRemark_type("Upd resource User");
            hisInfoMapper.insertInfo(his);
        }catch (Exception e){
            log.error("his_ifo表数据库插入失败"+e.getMessage());
        }
        return baseResult;
    }


    @Override
    public List<UserInfo> getUserList(String ip) {

        return  resourceMapper.getUserList(ip);
    }
}
