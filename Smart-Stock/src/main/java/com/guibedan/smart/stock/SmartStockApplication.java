package com.guibedan.smart.stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SmartStockApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartStockApplication.class, args);
	}

}
