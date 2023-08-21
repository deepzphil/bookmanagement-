package com.bookmanagement.bookmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestBookmanagementApplication {

	public static void main(String[] args) {
		SpringApplication.from(BookmanagementApplication::main).with(TestBookmanagementApplication.class).run(args);
	}

}
