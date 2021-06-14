package com.example.socksdemo.model;

import java.math.BigDecimal;
import java.util.Date;

public class OrderDataVo {

    private Integer order_id;
    private String order_no;
    private String order_email_name;
    private String order_good_name;
    private BigDecimal order_price;
    private String order_coupons_name;
    private Date order_time;
    private String order_status;
    private String order_remark;
    private String order_good_type;
    private BigDecimal order_pay_price;
    private String order_qr_type;
    private String order_type;
    private String order_validity;

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getOrder_email_name() {
        return order_email_name;
    }

    public void setOrder_email_name(String order_email_name) {
        this.order_email_name = order_email_name;
    }

    public String getOrder_good_name() {
        return order_good_name;
    }

    public void setOrder_good_name(String order_good_name) {
        this.order_good_name = order_good_name;
    }

    public BigDecimal getOrder_price() {
        return order_price;
    }

    public void setOrder_price(BigDecimal order_price) {
        this.order_price = order_price;
    }

    public String getOrder_coupons_name() {
        return order_coupons_name;
    }

    public void setOrder_coupons_name(String order_coupons_name) {
        this.order_coupons_name = order_coupons_name;
    }

    public Date getOrder_time() {
        return order_time;
    }

    public void setOrder_time(Date order_time) {
        this.order_time = order_time;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getOrder_remark() {
        return order_remark;
    }

    public void setOrder_remark(String order_remark) {
        this.order_remark = order_remark;
    }

    public String getOrder_good_type() {
        return order_good_type;
    }

    public void setOrder_good_type(String order_good_type) {
        this.order_good_type = order_good_type;
    }

    public String getOrder_qr_type() {
        return order_qr_type;
    }

    public void setOrder_qr_type(String order_qr_type) {
        this.order_qr_type = order_qr_type;
    }

    public BigDecimal getOrder_pay_price() {
        return order_pay_price;
    }

    public void setOrder_pay_price(BigDecimal order_pay_price) {
        this.order_pay_price = order_pay_price;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getOrder_validity() {
        return order_validity;
    }

    public void setOrder_validity(String order_validity) {
        this.order_validity = order_validity;
    }
}
