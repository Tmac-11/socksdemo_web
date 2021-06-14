package com.example.socksdemo.task;



import com.example.socksdemo.emailutil.QQMailUtil;
import com.example.socksdemo.emailutil.SendGmail;
import com.example.socksdemo.mapper.UserMapper;
import com.example.socksdemo.model.SearchVo;
import com.example.socksdemo.model.UserInfo;
import com.example.socksdemo.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserInfoTask {

    private static Log log= LogFactory.getLog(UserInfoTask.class);
    @Value("${email.send.username}")
    private String sendName;

    @Value("${email.receive.username}")
    private String receiveName;

    @Value("${email.code}")
    private String code;


    @Autowired
    UserService userService;

   @Scheduled(cron ="${task.cron}")
    public  void pushInfo(){
       log.info("推送开始..");
//        //失效用户
        List<UserInfo> list=failUser();
        String userlist=list.toString();
        String title="失效用户名单，请及时提醒";
        String msg="";
        if(list.size()!=0){
             msg=LocalDate.now()+" 失效用户名单:"+System.getProperty("line.separator")+userlist;
        }

       // System.out.println(msg);
        //近3天要失效的用户
        List<UserInfo> tipslist=tipsUser();
        if(tipslist.size()!=0){
            msg=msg+System.getProperty("line.separator")+"近3天即将失效用户："+System.getProperty("line.separator")+tipslist.toString();
        }

        if("".equals(msg)){
            log.info("今日无提醒消息！");
        }else{
            //发送邮件
            String arr[]= receiveName.split(",");
            for(int i=0;i<arr.length;i++){
                try {
                    SendGmail.gmailSender(sendName,receiveName,msg,code,title);
                }catch (Exception e){
                    log.error("邮件发送错误"+e.getMessage());
                }
            }
        }



    }

    /**
     * 用户发送邮件
     */
    @Scheduled(cron ="${task.user.cron}")
    public  void pushInfoToUser() {
        log.info("推送用户开始..");
        String title = "【服务即将到期，请及时续费】";
        String msg = "续费地址:\n" +
                "浏览器打开链接: http://www.flybirds.ga/homePage/authHtml\n" +
                "邀请码：2020";
        //近三天失效用户
        List<UserInfo> tipsUser = tipsUser();
        sendEmail(tipsUser,sendName,msg,code,title);

        String title1="【服务已到期，请及时续费】";
        //今天失效用户
        SearchVo searchVo=new SearchVo();
        searchVo.setEnddate(LocalDate.now());
        List<UserInfo> failUser=userService.queryUser(searchVo);
        sendEmail(failUser,sendName,msg,code,title1);
    }

    /**
     * 发送邮箱
     * @param list
     * @param sendName
     * @param msg
     * @param code
     * @param title
     */
    public static void sendEmail( List<UserInfo> list,String sendName,String msg,String code,String title) {
        for (int i = 0; i < list.size(); i++) {
            UserInfo userInfo = list.get(i);
            //验证邮箱名
            if (checkEmail(userInfo.getUser_name().trim())) {
                try {
                    SendGmail.gmailSender(sendName, userInfo.getUser_name().trim(), msg, code, title);
                } catch (Exception e) {
                    log.error("邮件发送错误" + e.getMessage());
                    log.info("发送邮件名：" + userInfo.getUser_name().trim());
                }
            }
        }
    }


     /**
     * 验证邮箱
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public List<UserInfo> tipsUser(){
        SearchVo searchVo=new SearchVo();
        searchVo.setStartdate(LocalDate.now());
        searchVo.setEnddate(LocalDate.now().plusDays(+3));
        List<UserInfo> list=userService.userTips(searchVo);
        return list;
    }

    public List<UserInfo>  failUser(){
        SearchVo searchVo=new SearchVo();
        searchVo.setEnddate(LocalDate.now());
        List<UserInfo> list=userService.queryUser(searchVo);
        userService.failUserList(list);
        return list;

    }
}
