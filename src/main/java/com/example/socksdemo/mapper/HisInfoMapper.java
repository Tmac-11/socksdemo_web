package com.example.socksdemo.mapper;

import com.example.socksdemo.model.SearchVo;
import com.example.socksdemo.model.UserHisInfo;

import java.util.List;

public interface HisInfoMapper {

    public  void insertInfo(UserHisInfo userHisInfo);
    public List<UserHisInfo>  hisInfo(SearchVo searchVo);
}
