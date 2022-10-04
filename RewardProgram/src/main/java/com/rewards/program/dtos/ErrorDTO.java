package com.rewards.program.dtos;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Ali BEN BRAHIM
 *
 */
@Data @NoArgsConstructor @AllArgsConstructor
public class ErrorDTO {
	private String message;
	private String errorDetail;
	private HttpStatus status;
	private LocalDateTime time;
}
