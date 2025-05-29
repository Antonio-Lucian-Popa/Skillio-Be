package com.asusoftware.Skillio_Be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SkillioBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkillioBeApplication.class, args);
	}

}
