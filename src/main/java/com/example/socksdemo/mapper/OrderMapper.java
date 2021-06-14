package com.example.socksdemo.mapper;

import com.example.socksdemo.model.OrderDataVo;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

public interface OrderMapper {

    public void insertOrder(@Param("orderDataVo") OrderDataVo orderDataVo);
    public void updOrder(HashMap<String, String> map);
    public OrderDataVo queryOrder(@Param("orderNo") String orderNo);
    public Integer queryUserCodeNum(@Param("userCode") String userCode);
    public String [] queryUserCodeInfo(@Param("userCode") String userCode);
    public void updValidity(@Param("orderNoStr") String[] orderNoStr);

}
