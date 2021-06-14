package com.example.socksdemo.mapper;

import com.example.socksdemo.model.UserAmount;

import java.util.List;

public interface AmountMapper {

    public  void insertAmount(UserAmount userAmount);
    public List<UserAmount> queryAmount(UserAmount userAmount);
    public void updInfo(UserAmount userAmount);
    public void delInfo(Integer amount_id);
}
