package com.example.socksdemo.model;

import java.util.List;

public class BaseResult {

    private String msg;
    private Object data;


    public BaseResult(){

    }

    public BaseResult(String msg, Object data) {
        this.msg = msg;
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }




}
