package com.example.socksdemo.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class ResParam {

    //用户名
    private String username;
    //资源类型
    private String resource_type;
    //加密方式
    private String key_type;
    private String ip;
    private String port;
    private String passwd;
    private String timeout;
    private String mode;
    private String fast_open;
    private String plugin;
    private String plugin_opts;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startdate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate enddate;
    private Double amount;
    private String select_type;
    private String select_ip;
    private String rights;
    private String order_good_type;
    private String user_type;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getResource_type() {
        return resource_type;
    }

    public void setResource_type(String resource_type) {
        this.resource_type = resource_type;
    }

    public String getKey_type() {
        return key_type;
    }

    public void setKey_type(String key_type) {
        this.key_type = key_type;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getFast_open() {
        return fast_open;
    }

    public void setFast_open(String fast_open) {
        this.fast_open = fast_open;
    }

    public String getPlugin() {
        return plugin;
    }

    public void setPlugin(String plugin) {
        this.plugin = plugin;
    }

    public String getPlugin_opts() {
        return plugin_opts;
    }

    public void setPlugin_opts(String plugin_opts) {
        this.plugin_opts = plugin_opts;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getSelect_type() {
        return select_type;
    }

    public void setSelect_type(String select_type) {
        this.select_type = select_type;
    }

    public String getSelect_ip() {
        return select_ip;
    }

    public void setSelect_ip(String select_ip) {
        this.select_ip = select_ip;
    }

    public String getRights() {
        return rights;
    }

    public void setRights(String rights) {
        this.rights = rights;
    }

    public String getOrder_good_type() {
        return order_good_type;
    }

    public void setOrder_good_type(String order_good_type) {
        this.order_good_type = order_good_type;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
}
