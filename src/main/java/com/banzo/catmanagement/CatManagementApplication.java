package com.banzo.catmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class CatManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatManagementApplication.class, args);
	}

}
