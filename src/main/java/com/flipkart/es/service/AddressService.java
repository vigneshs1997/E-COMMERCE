package com.flipkart.es.service;

import org.springframework.http.ResponseEntity;

import com.flipkart.es.requestdto.AddressRequest;
import com.flipkart.es.responsedto.AddressResponse;
import com.flipkart.es.util.ResponseStructure;

public interface AddressService {

	ResponseEntity<ResponseStructure<AddressResponse>> addAddressToStore(int storeId, AddressRequest addressRequest);/*based on Id only 
	                                                                                                                   we are adding address to store*/

}
