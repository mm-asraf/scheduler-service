package com.asraf.schedulerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@SpringBootApplication
public class SchedulerserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchedulerserviceApplication.class, args);
	}
	
	@Bean
	public TaskScheduler taskScheduler() {
		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
		return taskScheduler;
	}

}
