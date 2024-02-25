package com.flipkart.es.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NoStoredDataExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

}

/*
 * RuntimeException is the superclass of thoseexceptions that can be thrown during the normal operation of theJava Virtual Machine. 
   RuntimeException and its subclasses are uncheckedexceptions.
   Unchecked exceptions do not need to bedeclared in a method or constructor's throws clause
    if theycan be thrown by the execution of the method or constructor andpropagate outside the method or constructor boundary.
*/
