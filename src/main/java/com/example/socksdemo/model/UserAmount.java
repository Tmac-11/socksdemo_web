package com.example.socksdemo.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public class UserAmount {

    private Integer amount_id;
    private Integer user_id;
    private String user_name;
    private String amount_date;
    private BigDecimal user_amount;
    private String  amount_type;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate amount_creattime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate start;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end;

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public BigDecimal getUser_amount() {
        return user_amount;
    }

    public void setUser_amount(BigDecimal user_amount) {
        this.user_amount = user_amount;
    }

    public String getAmount_type() {
        return amount_type;
    }

    public void setAmount_type(String amount_type) {
        this.amount_type = amount_type;
    }

    public LocalDate getAmount_creattime() {
        return amount_creattime;
    }

    public void setAmount_creattime(LocalDate amount_creattime) {
        this.amount_creattime = amount_creattime;
    }

    public String getAmount_date() {
        return amount_date;
    }

    public void setAmount_date(String amount_date) {
        this.amount_date = amount_date;
    }

    public Integer getAmount_id() {
        return amount_id;
    }

    public void setAmount_id(Integer amount_id) {
        this.amount_id = amount_id;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }
}
