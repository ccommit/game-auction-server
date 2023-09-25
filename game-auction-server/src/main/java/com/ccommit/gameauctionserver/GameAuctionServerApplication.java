package com.ccommit.gameauctionserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@MapperScan
@EnableAspectJAutoProxy
@SpringBootApplication
public class GameAuctionServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameAuctionServerApplication.class, args);
	}

}
