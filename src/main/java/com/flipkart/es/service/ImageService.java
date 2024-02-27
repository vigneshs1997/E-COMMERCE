package com.flipkart.es.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.flipkart.es.util.ResponseStructure;

public interface ImageService {

	ResponseEntity<ResponseStructure<String>> addStoreImage(int storeId, MultipartFile image);

	ResponseEntity<byte[]> getImage(String imageId);

	
}
