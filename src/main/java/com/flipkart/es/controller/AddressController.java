package com.flipkart.es.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flipkart.es.requestdto.AddressRequest;
import com.flipkart.es.responsedto.AddressResponse;
import com.flipkart.es.service.AddressService;
import com.flipkart.es.util.ResponseStructure;

import lombok.AllArgsConstructor;

@RestController//it contains @controller and (@responsebody=> it gives exact data mentioned by us to client)
@AllArgsConstructor //constructor accepts all the arquments
@RequestMapping("/api/v1")//it is general http methods=> like versions(updates) whatsapp=>(If we updates, we will give version 2) =>global url
public class AddressController {

	private AddressService addressService;
	
	@PostMapping("/stores/{storeId}/addresses")
	private ResponseEntity<ResponseStructure<AddressResponse>>addAddressToStore(@PathVariable int storeId,@RequestBody AddressRequest addressRequest){
		return addressService.addAddressToStore(storeId,addressRequest);
		
	}

}
