package com.flipkart.es.serviceimpl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flipkart.es.requestdto.AddressRequest;
import com.flipkart.es.responsedto.AddressResponse;
import com.flipkart.es.service.AddressService;
import com.flipkart.es.util.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {

	@Override
	public ResponseEntity<ResponseStructure<AddressResponse>> addAddressToStore(int storeId,AddressRequest addressRequest) {
		
		return null;
	}

}
