package com.flipkart.es.exceptionhandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.flipkart.es.exception.NoStoredDataExistsException;
import com.flipkart.es.exception.SellerNotFoundByIdException;
import com.flipkart.es.exception.StoreAlreadyExistWithSellerException;
import com.flipkart.es.exception.StoreNotFoundByIdException;
import com.google.common.net.HttpHeaders;

@RestControllerAdvice
public class StoreApplicationHandler extends ResponseEntityExceptionHandler{
	
	
	public ResponseEntity<Object> structure(HttpStatus status, String message, Object rootCause) {
		return new ResponseEntity<Object>(Map.of("status", status.value(),
				                                 "message", message,
				                                 "root cause", rootCause),
				                                                status);
	}
	
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		List<ObjectError> allErrors = ex.getAllErrors();//To get the list of errors

		Map<String, String> errors = new HashMap<String, String>();//To display in the form of Key Value Pair ***

		allErrors.forEach(error -> {//To iterate each error

			FieldError fieldError = (FieldError) error;//Field Error is nothing but an error occurred to the Field.
			errors.put(fieldError.getField(), fieldError.getDefaultMessage());

		});

		return structure(HttpStatus.BAD_REQUEST, "Failed to save the data", errors);
	}
    
    @ExceptionHandler(StoreAlreadyExistWithSellerException.class)//exception handling at compile time so exception ends with (.class)
    private ResponseEntity<Object> handleStoreAlreadyExistWithSellerException(StoreAlreadyExistWithSellerException exp){
		return structure(HttpStatus.FOUND, exp.getMessage(), "Store Data Already Found for this Seller");
    	
    }
    
    @ExceptionHandler(StoreNotFoundByIdException.class)//exception handling at compile time so exception ends with (.class)
    private ResponseEntity<Object> handleStoreNotFoundByIdException(StoreNotFoundByIdException exp){
		return structure(HttpStatus.NOT_FOUND,exp.getMessage(),"Store Data not exists with this ID");
    	
    }
    
    @ExceptionHandler(NoStoredDataExistsException.class)//exception handling at compile time so exception ends with (.class)
    private ResponseEntity<Object> handleNoStoredDataExistsException(NoStoredDataExistsException exp){//Object accepts all entity class
		return structure(HttpStatus.NOT_FOUND,exp.getMessage(),"Store list is empty");
    	
    }
    
    @ExceptionHandler(SellerNotFoundByIdException.class)
    private ResponseEntity<Object> handleSellerNotFoundByIdException(SellerNotFoundByIdException exp){
		return structure(HttpStatus.NOT_FOUND,exp.getMessage(),"seller does not have store");
    	
    }
    
     
}
