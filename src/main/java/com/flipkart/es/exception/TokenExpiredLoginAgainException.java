package com.flipkart.es.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class TokenExpiredLoginAgainException extends RuntimeException {

	
	
	private String message;

}
