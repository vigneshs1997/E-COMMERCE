package com.flipkart.es.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import com.flipkart.es.enums.ImageType;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Document(collection = "image")
@Getter
@Setter
public class Image {

 @org.springframework.data.annotation.Id//(because it is mongoDb)
 private String	imageId; //imageId should be in string 
 private ImageType imageType;
 private byte[] imageBytes;
 private String contentType;//jpeg,png
 
 
 //attributes
 
	
}
