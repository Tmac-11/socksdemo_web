package com.example.socksdemo.emailutil;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.Properties;


public class QQMailUtil {

    public static void sendMessageToMail(String sendQQ,String sendToQQ,String content,String code,String subject) throws GeneralSecurityException, MessagingException {


        Properties pro = new Properties();
        //        开启debug调试
       // pro.setProperty("mail.debug", "true");
        //        发送服务器需要身份验证
        pro.setProperty("mail.smtp.auth", "true");
        //        设置邮件服务器主机名
        pro.setProperty("mail.host", "smtp.qq.com");
        //        发送邮件协议名称
        pro.setProperty("mail.transport.protocol","smtp");


        pro.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        pro.setProperty("mail.smtp.port", "465");
        pro.setProperty("mail.smtp.socketFactory.port", "465");



        Session session = Session.getInstance(pro);
        session.setDebug(true);
        Message msg = new MimeMessage(session);
        msg.setSubject(subject);
        msg.setText(content);
        //  设置发送人
        msg.setFrom(new InternetAddress(sendQQ));

        Transport transport = session.getTransport();
        // 设置密令
        transport.connect("smtp.qq.com", sendQQ, code);
        //设置收件人
        transport.sendMessage(msg, new Address[]{new InternetAddress(sendToQQ)});
        transport.close();
    }

    public static void main(String[] args) {
        System.out.println(LocalDateTime.now());
    }
}
