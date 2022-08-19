package com.estudo.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableSpringDataWebSupport
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MvcApplication {
	

	public static void main(String[] args) {
		SpringApplication.run(MvcApplication.class, args);
	}

}
