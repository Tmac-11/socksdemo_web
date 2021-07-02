package com.example.socksdemo.service.impl;

import com.example.socksdemo.mapper.SettingMapper;
import com.example.socksdemo.mapper.UserMapper;

import com.example.socksdemo.model.UserInfo;
import com.example.socksdemo.service.RewardService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
public class RewardServiceImpl implements RewardService{

    private static Log log= LogFactory.getLog(RewardServiceImpl.class);

    @Autowired
    UserMapper userMapper;
    @Autowired
    SettingMapper settingMapper;


    @Async
    @Override
    public void addUseTime(String userCode) {

        UserInfo userInfo = userMapper.queryUserCode(userCode);
        if(userInfo==null){
            log.info("推荐码："+userCode+" 系统不存在。");
            return;
        }
        Integer settingVar = settingMapper.querySetting();
        LocalDate enddate = userInfo.getEnddate();
        //如果结束日期比现在日期小，说明不在有效期内
        if(enddate.isBefore(LocalDate.now())){
            log.info("推荐码为:"+userCode+" 用户,已到期不增加日期。");
           return;
        }
        userInfo.setEnddate(enddate.plusDays(settingVar));
        userMapper.updUser(userInfo);
        log.info("推荐码为:"+userCode+" 用户,结束日期是:"+enddate+",有效期增加:"+settingVar+" 天");
    }

    public static void main(String[] args) {
        LocalDate now = LocalDate.now();
        LocalDate parse = LocalDate.parse("2021-06-25");
        boolean after = parse.isBefore(now);
        System.out.println(after);

    }
}
