package com.techleads.app.router.a;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class MyFistTimerRouter extends RouteBuilder{
	@Autowired
	private CurrentTimeBean currentTimeBean;
	@Autowired
	private SimpleLoggingProcessingComponent simpleLoggingProcessingComponent;

	@Override
	public void configure() throws Exception {
		//queue ->timer
		//transformation 
		//database-> log
		//Exchange[ExchangePattern: InOnly, BodyType: null, Body: [Body is null]]
		
		from("timer: first-timer")
		.log("${body}")
		.transform()
		
		.constant("My Constant Message")
		.log("${body}")
//		.constant("Time now is "+LocalDate.now())
		
		
		//processing
		//Transformation 
		
		.bean(currentTimeBean,"getCurrentTime")
		.log("${body}")
		.bean(simpleLoggingProcessingComponent,"process")
		.log("${body}")
		.process(new SimpleLoggingProcessor())
		.to("log: first-timer")
		;
		
		
		
	}

}

@Component
class CurrentTimeBean{
	public String getCurrentTime() {
		return "Time now is "+LocalDateTime.now();
	}
}


@Component
class SimpleLoggingProcessingComponent{
	private Logger logger=LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);
	public void process(String msg) {
		logger.info("SimpleLoggingProcessingComponent {}", msg);
	}
}

