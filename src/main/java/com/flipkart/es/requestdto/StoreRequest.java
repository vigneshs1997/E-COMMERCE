package com.flipkart.es.requestdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreRequest {

	private int storeId;
	private String storeName;
	private String logoLink;
	private String about;
}
