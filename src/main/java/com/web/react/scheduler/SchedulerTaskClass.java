package com.web.react.scheduler;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchedulerTaskClass {
	
	Logger log = LoggerFactory.getLogger(getClass());

	public void doJob() {
		LocalDateTime ldt = LocalDateTime.now();
		String now = String.valueOf(ldt.getYear()) + "-" + 
					 String.valueOf(ldt.getMonthValue()) + "-" + 
					 String.valueOf(ldt.getDayOfMonth()) + " " +
					 String.valueOf(ldt.getHour()) + ":" +
					 String.valueOf(ldt.getMinute()) + ":" +
					 String.valueOf(ldt.getSecond()) + " ... " +
					 String.valueOf(ldt.getNano());
		log.debug("excute scheduler .... : {}", now);
	}
}
