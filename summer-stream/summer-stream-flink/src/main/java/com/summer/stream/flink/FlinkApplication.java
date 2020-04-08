package com.summer.stream.flink;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class FlinkApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlinkApplication.class, args);
	}


}
