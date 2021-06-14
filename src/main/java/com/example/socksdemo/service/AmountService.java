package com.example.socksdemo.service;

import com.example.socksdemo.model.UserAmount;

import java.util.List;

public interface AmountService {

    public void  insertAmount (UserAmount userAmount) throws Exception ;
    public List<UserAmount> queryInfo(UserAmount userAmount);
    public void updAmount(UserAmount userAmount);
    public  void delAmount(Integer amount_id);
}
