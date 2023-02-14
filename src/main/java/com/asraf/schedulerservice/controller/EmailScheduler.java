package com.asraf.schedulerservice.controller;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

import javax.validation.Valid;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.asraf.schedulerservice.model.EmailRequest;
import com.asraf.schedulerservice.model.EmailResponse;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class EmailScheduler {

	@Autowired
	private Scheduler scheduler;

	@PostMapping("/schedule/email")

	public ResponseEntity<EmailResponse> schedulEmail(@Valid @RequestBody EmailRequest emailRequest) {
		try {
			ZonedDateTime zonedDateTime = ZonedDateTime.of(emailRequest.getDateTime(), emailRequest.getTimezone());


		} catch (SchedulerException e) {
			log.error("error while scheduling email",e);
			EmailResponse emailResponse = new EmailResponse(false, "error while scheduling! please try again");
			
		}
	}

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
