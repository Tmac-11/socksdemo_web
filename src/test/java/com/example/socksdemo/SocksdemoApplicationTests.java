package com.example.socksdemo;



import com.example.socksdemo.mapper.SrcFlowDataMapper;
import com.example.socksdemo.mapper.UserMapper;
import com.example.socksdemo.model.SearchVo;
import com.example.socksdemo.model.UserInfo;
import com.example.socksdemo.service.FlowDataService;
import com.example.socksdemo.service.UserService;
import com.example.socksdemo.service.impl.FlowDataServiceImpl;
import com.example.socksdemo.task.AnalyticalDataTask;
import com.example.socksdemo.task.UserInfoTask;
import com.example.socksdemo.utils.InvitationCodeUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.Socket;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest

public class SocksdemoApplicationTests {

	private static Log log= LogFactory.getLog(SocksdemoApplicationTests.class);
	@Autowired
	FlowDataService flowDataService;
	@Autowired
	SrcFlowDataMapper srcFlowDataMapper;
	@Autowired
	UserInfoTask userInfoTask;
	@Autowired
	UserMapper userMapper;
//	@Autowired
//	AnalyticalDataTask analyticalDataTask;
	@Autowired
	UserService userService;



	@Test
	  public void contextLoads(){

		LocalDateTime start=LocalDateTime.now();
		//String msg=flowDataService.dataProcess("2019-12-26");
		String msg=flowDataService.dataByDate("2020-03-19");
		LocalDateTime end=LocalDateTime.now();
		Duration duration=Duration.between(start,end);
		System.out.println("开始时间："+start);
		System.out.println("结束时间："+end);
		System.out.println("执行时间："+duration.toMinutes());
		System.out.println(msg);
//		String date="2020_2_10_";
//
//			for(int i=0;i<24;i++){
//				try{
//				String msg=flowDataService.dataProcess(date+i);
//				System.out.println(date+i+"--"+msg);
//				}catch (Exception e){
//					log.error("test error:"+e.getMessage());
//				}
//
//			}




	}

	@Test
	public void test(){
	  	String dateParm="2020-03-19";
		//String ip="38.143.19.31";
		//String delArr []={"63366","47216"};

	//	String portString []=srcFlowDataMapper.flowDataByIp(dateParm,ip,delArr);
	//	System.out.println(portString.length);
		LocalDateTime start=LocalDateTime.now();
		String msg=flowDataService.dataByProcedureDate(dateParm);
		LocalDateTime end=LocalDateTime.now();
		Duration duration=Duration.between(start,end);
		System.out.println("开始时间："+start);
		System.out.println("结束时间："+end);
		System.out.println("执行时间："+duration.toMinutes());
		System.out.println(msg);

	}

	@Test
	public void mysql(){
		SearchVo searchVo=new SearchVo();
		searchVo.setStartdate(LocalDate.now());
		searchVo.setEnddate(LocalDate.now().plusDays(+3));
		List<UserInfo> list=userService.userTips(searchVo);
//		SearchVo searchVo=new SearchVo();
//		searchVo.setEnddate(LocalDate.now());
//		List<UserInfo> list=userService.queryUser(searchVo);
		for(int i=0;i<list.size();i++){
			System.out.println(list.get(i).toString());
		}

	}

	@Test
	public void updCode(){
		List<UserInfo> list=userMapper.queryByCode();
		for(int i=0;i<list.size();i++){
			UserInfo userInfo=list.get(i);
			userInfo.setUser_code(InvitationCodeUtil.toSerialCode(Long.valueOf(userInfo.getUser_id())));
			userMapper.updUserCode(userInfo);
		}

	}

}
