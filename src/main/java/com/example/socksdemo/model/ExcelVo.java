package com.example.socksdemo.model;

import com.example.socksdemo.ExcelUtil.Excel;
import com.example.socksdemo.ExcelUtil.ExcelColor;

import java.math.BigDecimal;

public class ExcelVo {

    @Excel(width = 15, value = "用户名",bgColor = ExcelColor.WHITE)
    private String name;
    @Excel(width = 15, value = "金额",bgColor = ExcelColor.WHITE)
    private BigDecimal amount;
    @Excel(width = 15, value = "帐单类型",bgColor = ExcelColor.WHITE)
    private String type;
    @Excel(width = 20, value = "有效时间",bgColor = ExcelColor.WHITE)
    private String time;
    @Excel(width = 15, value = "创建时间",bgColor = ExcelColor.WHITE)
    private String createtime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
}
