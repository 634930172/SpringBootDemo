package com.john.springbootdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SpringbootdemoApplication {
	//extends SpringBootServletInitializer war第一步
	public static void main(String[] args) {
		SpringApplication.run(SpringbootdemoApplication.class, args);
	}

//	//重写此方法为了打包到linux 打war包时用到
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder.sources(SpringbootdemoApplication.class);
//	}
}

