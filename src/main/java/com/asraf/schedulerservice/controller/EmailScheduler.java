package com.asraf.schedulerservice.controller;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import com.asraf.schedulerservice.model.EmailRequest;

public class EmailScheduler {
	
	private JobDetail builJobDetail(EmailRequest schedulEmailRequest) {
		JobDataMap jobDataMap = new JobDataMap();
		
		jobDataMap.put("email", schedulEmailRequest.getEmail());
		jobDataMap.put("subject", schedulEmailRequest.getSubject());
		jobDataMap.put("body", schedulEmailRequest.getBody());
		
		return JobBuilder.newJob()
				.withIdentity(UUID.randomUUID().toString(),"email-jobs")
				.withDescription("send email job")
				.usingJobData(jobDataMap)
				.storeDurably()
				.build();
	}
	
	private Trigger buildTrigger(JobDetail jobDetail, ZonedDateTime startAt) {
	     return TriggerBuilder.newTrigger()
	            .forJob(jobDetail)
	            .withIdentity(jobDetail.getKey().getName(),"email-triggers")
	            .withDescription("send email trigger")
	            .startAt(Date.from(startAt.toInstant()))
	            .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
	            .build();
	         
	                                                    
	            
	}
}
