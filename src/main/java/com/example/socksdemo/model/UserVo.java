package com.example.socksdemo.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class UserVo {

    private Integer user_id;
    private String user_name;
    private String user_password;
    private LocalDate startdate;
    private LocalDate enddate;
    private Integer resource_id;
    private LocalDateTime user_creattime;
    private String user_type;
    private String user_code;
    private Integer user_rights;


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

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public LocalDate getStartdate() {
        return startdate;
    }

    public void setStartdate(LocalDate startdate) {
        this.startdate = startdate;
    }

    public LocalDate getEnddate() {
        return enddate;
    }

    public void setEnddate(LocalDate enddate) {
        this.enddate = enddate;
    }

    public Integer getResource_id() {
        return resource_id;
    }

    public void setResource_id(Integer resource_id) {
        this.resource_id = resource_id;
    }

    public LocalDateTime getUser_creattime() {
        return user_creattime;
    }

    public void setUser_creattime(LocalDateTime user_creattime) {
        this.user_creattime = user_creattime;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public Integer getUser_rights() {
        return user_rights;
    }

    public void setUser_rights(Integer user_rights) {
        this.user_rights = user_rights;
    }
}
