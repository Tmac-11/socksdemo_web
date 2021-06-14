package com.example.socksdemo.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class ResUpdParam {

    private String user_id;
    private String resource_id;
    private String user_name;
    private String newIp;
    private String newPort;
    private String newPassword;
    private String resource_state;
    private String user_creattime;
    private String resource_type;
    private String new_resource_type;
    private String new_key_type;
    private String new_timeout;
    private String new_mode;
    private String new_fast_open;
    private String new_plugin;
    private String new_plugin_opts;
    private String newDomainName;
    private String user_code;
    private Integer user_rights;
    private  String user_type;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate new_startdate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate new_enddate;
    private String forced_state;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getResource_id() {
        return resource_id;
    }

    public void setResource_id(String resource_id) {
        this.resource_id = resource_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getNewIp() {
        return newIp;
    }

    public void setNewIp(String newIp) {
        this.newIp = newIp;
    }

    public String getNewPort() {
        return newPort;
    }

    public void setNewPort(String newPort) {
        this.newPort = newPort;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getResource_state() {
        return resource_state;
    }

    public void setResource_state(String resource_state) {
        this.resource_state = resource_state;
    }

    public String getUser_creattime() {
        return user_creattime;
    }

    public void setUser_creattime(String user_creattime) {
        this.user_creattime = user_creattime;
    }

    public String getNew_resource_type() {
        return new_resource_type;
    }

    public void setNew_resource_type(String new_resource_type) {
        this.new_resource_type = new_resource_type;
    }

    public String getNew_key_type() {
        return new_key_type;
    }

    public void setNew_key_type(String new_key_type) {
        this.new_key_type = new_key_type;
    }

    public String getNew_timeout() {
        return new_timeout;
    }

    public void setNew_timeout(String new_timeout) {
        this.new_timeout = new_timeout;
    }

    public String getNew_mode() {
        return new_mode;
    }

    public void setNew_mode(String new_mode) {
        this.new_mode = new_mode;
    }

    public String getNew_fast_open() {
        return new_fast_open;
    }

    public void setNew_fast_open(String new_fast_open) {
        this.new_fast_open = new_fast_open;
    }

    public String getNew_plugin() {
        return new_plugin;
    }

    public void setNew_plugin(String new_plugin) {
        this.new_plugin = new_plugin;
    }

    public String getNew_plugin_opts() {
        return new_plugin_opts;
    }

    public void setNew_plugin_opts(String new_plugin_opts) {
        this.new_plugin_opts = new_plugin_opts;
    }

    public String getResource_type() {
        return resource_type;
    }

    public void setResource_type(String resource_type) {
        this.resource_type = resource_type;
    }

    public String getNewDomainName() {
        return newDomainName;
    }

    public void setNewDomainName(String newDomainName) {
        this.newDomainName = newDomainName;
    }

    public LocalDate getNew_startdate() {
        return new_startdate;
    }

    public void setNew_startdate(LocalDate new_startdate) {
        this.new_startdate = new_startdate;
    }

    public LocalDate getNew_enddate() {
        return new_enddate;
    }

    public void setNew_enddate(LocalDate new_enddate) {
        this.new_enddate = new_enddate;
    }

    public String getForced_state() {
        return forced_state;
    }

    public void setForced_state(String forced_state) {
        this.forced_state = forced_state;
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

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
}
