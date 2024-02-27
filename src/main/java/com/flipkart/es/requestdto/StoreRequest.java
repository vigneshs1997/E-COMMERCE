package com.flipkart.es.requestdto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreRequest {

	@NotEmpty(message="Please enter store name")
	private String storeName;
	private String about;
}
