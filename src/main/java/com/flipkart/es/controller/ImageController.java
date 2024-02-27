package com.flipkart.es.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.flipkart.es.service.ImageService;
import com.flipkart.es.util.ResponseStructure;

import lombok.AllArgsConstructor;

@RestController//it contains @controller and (@responsebody=> it gives exact data mentioned by us to client)
@AllArgsConstructor//it is used to constructor for the reference variable 
@RequestMapping("/api/v1")//it is general http methods=> like versions(updates) whatsapp=>(If we updates, we will give version 2) =>global(url)
public class ImageController {
	
	private ImageService imageService;

	@PostMapping("/stores/{storeId}/images")
	public ResponseEntity<ResponseStructure<String>>addStoreImage(@PathVariable int storeId,MultipartFile image){
		return imageService.addStoreImage(storeId,image) ;
		
	}
	
	@GetMapping("/images/{imageId}")
	public ResponseEntity<byte[]> getImage(@PathVariable String imageId){
		return imageService.getImage(imageId);
	}
}
