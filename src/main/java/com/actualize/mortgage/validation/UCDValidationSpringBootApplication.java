package com.actualize.mortgage.validation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UCDValidationSpringBootApplication {
	private static final Logger LOG = LogManager.getLogger(UCDValidationSpringBootApplication.class);

	public static void main(String[] args) {
		LOG.info("Started UCD Validation service");
		SpringApplication.run(UCDValidationSpringBootApplication.class, args);
	}
}
