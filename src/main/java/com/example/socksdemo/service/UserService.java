package com.example.socksdemo.service;

import com.example.socksdemo.model.*;

import java.util.List;

public interface UserService {

    public BaseResult ConfigUser(ResParam resParam);
    public  List<UserInfo> userlist();
    public List<UserInfo> userFailure();
    public void deleteUser(Integer userid,Integer resid,String resource_sate);
    public List<UserInfo> queryUser(SearchVo searchVo);
    public String failUser(Integer user_id,Integer res_id);
    public void failUserList(List<UserInfo> list);
    public  List<UserInfo> userTips(SearchVo searchVo);
    public Integer insertUser(UserVo userVo);
    public String updUser(UserInfo userinfo);
    public List<UserHisInfo> queryHisInfo(SearchVo searchVo);
}
