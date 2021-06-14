package com.example.socksdemo;

import com.example.socksdemo.model.SearchVo;
import com.example.socksdemo.model.UserInfo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;
import java.util.List;

@EnableScheduling
@SpringBootApplication
@MapperScan(basePackages = { "com.example.socksdemo.mapper"})
public class SocksdemoApplication {


	public static void main(String[] args) {
		SpringApplication.run(SocksdemoApplication.class, args);
	}

}
