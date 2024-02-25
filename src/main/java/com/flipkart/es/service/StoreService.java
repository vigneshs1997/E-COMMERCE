package com.flipkart.es.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.flipkart.es.requestdto.StoreRequest;
import com.flipkart.es.responsedto.StoreResponse;
import com.flipkart.es.util.ResponseStructure;

import jakarta.validation.Valid;

public interface StoreService {//interface accepts abstract method only

	ResponseEntity<ResponseStructure<StoreResponse>> addStore(StoreRequest request);

	ResponseEntity<ResponseStructure<StoreResponse>> updateStore(@Valid StoreRequest request, int storeId);

	ResponseEntity<ResponseStructure<List<StoreResponse>>> fetchAllStores();

	ResponseEntity<ResponseStructure<StoreResponse>> fetchStoreById(int storeId);

	ResponseEntity<ResponseStructure<StoreResponse>> fetchStoreBySeller(int sellerId);

	

	

	

}
