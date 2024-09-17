package com.codewithomarm.rosterup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RosterupApplication {
	private static final Logger logger = LoggerFactory.getLogger(
			RosterupApplication.class
	);

	public static void main(String[] args) {
		SpringApplication.run(RosterupApplication.class, args);
		logger.info("RosterupApplication started");
	}

}
