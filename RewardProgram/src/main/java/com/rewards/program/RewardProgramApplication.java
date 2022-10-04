package com.rewards.program;

import java.time.Clock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 *
 * @author Ali BEN BRAHIM
 *
 */
@SpringBootApplication
public class RewardProgramApplication {

	public static void main(String[] args) {
		SpringApplication.run(RewardProgramApplication.class, args);
	}
	
	@Bean
	public Clock clock() {
		return Clock.systemDefaultZone();
	}

}
