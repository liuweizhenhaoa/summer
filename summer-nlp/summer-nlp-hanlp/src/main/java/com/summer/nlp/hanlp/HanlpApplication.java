package com.summer.nlp.hanlp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class HanlpApplication {

	public static void main(String[] args) {
		SpringApplication.run(HanlpApplication.class, args);
	}


}
