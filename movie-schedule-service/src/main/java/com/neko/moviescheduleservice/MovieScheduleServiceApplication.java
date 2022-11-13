package com.neko.moviescheduleservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MovieScheduleServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieScheduleServiceApplication.class, args);
	}

}
