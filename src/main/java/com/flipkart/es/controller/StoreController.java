package com.flipkart.es.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flipkart.es.requestdto.StoreRequest;
import com.flipkart.es.responsedto.StoreResponse;
import com.flipkart.es.service.StoreService;
import com.flipkart.es.util.ResponseStructure;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController//it is the controller class
@AllArgsConstructor //constructor accepts all the arquments
@RequestMapping("/api/v1")//giving global link
public class StoreController {
	
	private StoreService storeService;//service class
	
	//accepts as request  from client and return as a response to the client
	@PreAuthorize("hasAuthority('SELLER')")//seller only can use the store
	@PostMapping("/stores")//API or endPoint
	public ResponseEntity<ResponseStructure<StoreResponse>> addStore(@RequestBody @Valid  StoreRequest request){//taking values from store request
		                                                                                                  //@Valid gives validation of that field of mail and so on
		return storeService.addStore(request);//storing request in storeService object and return to service class 
		
	}
	
	//accepts as request  from client and return as a response to the client
	@PreAuthorize("hasAuthority('SELLER')")
	@PutMapping("/stores/{storeId}")
	public ResponseEntity<ResponseStructure<StoreResponse>>updateStore(@RequestBody @Valid StoreRequest request,@PathVariable int storeId){
		return storeService.updateStore(request,storeId);//storing request in storeService object and return to service class
		
	}
	
	@GetMapping("/stores")
	public ResponseEntity<ResponseStructure<List<StoreResponse>>>fetchAllStores(){
		return storeService.fetchAllStores();
		
	}
	
	@GetMapping("/stores/{storeId}")
    public ResponseEntity<ResponseStructure<StoreResponse>>fetchStoreById(@PathVariable int storeId){
		return storeService.fetchStoreById(storeId);
    	
    }
	
	@GetMapping("/sellers/{sellerId}/stores")//go to seller and seller has sellerId and use that in store
	public ResponseEntity<ResponseStructure<StoreResponse>> fetchStoreBySeller(@PathVariable int sellerId){
		return storeService.fetchStoreBySeller(sellerId);
		
	}

}
