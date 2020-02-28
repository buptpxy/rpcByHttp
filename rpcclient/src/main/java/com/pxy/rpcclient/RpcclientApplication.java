package com.pxy.rpcclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RpcclientApplication {

	public static void main(String[] args) {
		SpringApplication.run(RpcclientApplication.class, args);
		System.out.println("rpc client start at port 8001!!!");
		System.out.println("You can trigger RPC calls through the following interfaces:");
		System.out.println("http://127.0.0.1:8001/getUserInfo");
	}

}
