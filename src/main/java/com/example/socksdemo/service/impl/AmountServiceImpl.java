package com.example.socksdemo.service.impl;

import com.example.socksdemo.mapper.AmountMapper;
import com.example.socksdemo.model.UserAmount;
import com.example.socksdemo.service.AmountService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AmountServiceImpl implements AmountService{

    @Autowired
    AmountMapper amountMapper;

    private static Log log= LogFactory.getLog(AmountServiceImpl.class);

    @Override
    public void insertAmount(UserAmount userAmount) throws Exception {
        amountMapper.insertAmount(userAmount);
    }

    @Override
    public List<UserAmount> queryInfo(UserAmount userAmount) {
        return amountMapper.queryAmount(userAmount);
    }

    @Override
    public void updAmount(UserAmount userAmount) {
        try {
            amountMapper.updInfo(userAmount);
        }catch (Exception e){
            log.error("帐单更新错误："+e.getMessage());
        }

    }


    @Override
    public void delAmount(Integer amount_id) {
        try {
            amountMapper.delInfo(amount_id);
        }catch (Exception e){
            log.error("帐单删除错误："+e.getMessage());
        }

    }
}
