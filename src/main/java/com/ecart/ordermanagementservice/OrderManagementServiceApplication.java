package com.ecart.ordermanagementservice;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@EnableAutoConfiguration
public class OrderManagementServiceApplication {
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(OrderManagementServiceApplication.class, args);
	}

}
