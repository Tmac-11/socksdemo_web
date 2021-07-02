package com.example.socksdemo.mapper;

import com.example.socksdemo.model.PageInfo;
import com.example.socksdemo.model.SearchVo;
import com.example.socksdemo.model.UserInfo;
import com.example.socksdemo.model.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {



    public Integer addUser(UserVo userVo);
    public List<UserInfo> userInfo();
    public List<UserInfo> userFailure();
    public void updUser(UserInfo userInfo);
    public void delUser(Integer user_id);
    public List<UserInfo> queryUser(SearchVo searchVo);
    public UserInfo queryByid(Integer user_id);
    public List<UserInfo> userTips(SearchVo searchVo);
    public String queryByResid( @Param("ipaddr") String ipaddr, @Param("port")String port);
    public Integer userInfoNum(PageInfo pageInfo);

    public List<UserInfo> queryByCode();
    public void updUserCode(UserInfo userInfo);

    public UserInfo queryUserCode(String userCode);

}
