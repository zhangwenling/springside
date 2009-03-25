package org.springside.examples.showcase.schedule;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 简单的定时任务类.
 */
public class ExampleJob {
	Logger logger = LoggerFactory.getLogger(ExampleJob.class);
	
	public void execute(){
		logger.info("Hello, now is "+new Date());
		System.out.println("Hello, now is "+new Date());
	}

}
