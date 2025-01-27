package com.santanu.Test.generter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@Configuration
@EnableSwagger2
public class TestGenerterApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestGenerterApplication.class, args);
	}

}
