package com.flipkart.es.serviceimpl;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.flipkart.es.entity.Image;
import com.flipkart.es.entity.StoreImage;
import com.flipkart.es.enums.ImageType;
import com.flipkart.es.exception.ImageNotFoundByIdException;
import com.flipkart.es.exception.StoreNotFoundByIdException;
import com.flipkart.es.repository.ImageRepository;
import com.flipkart.es.repository.StoreRepository;
import com.flipkart.es.service.ImageService;
import com.flipkart.es.util.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor//it will throw null pointer Exception if @AllArgsConstructor is not mentioned
public class ImageServiceImpl implements ImageService {
	
	private StoreRepository storeRepo;
	private ImageRepository imageRepo;
	
	@Override
	public ResponseEntity<ResponseStructure<String>> addStoreImage(int storeId, MultipartFile image) {//getting Id and MultipartFile(image)-interface
		return storeRepo.findById(storeId).map(store ->{//optional is a class which map().orElseThrow
			
		StoreImage storeImage=new StoreImage(); //creating object of storeImage(container)
		storeImage.setStoreId(storeId); //storing storeId in storeImage container otherwise storeId is null exception
		storeImage.setImageType(ImageType.LOGO);//storing imageType in storeImage container otherwise ImageType is null exception
		storeImage.setContentType(image.getContentType());//storing mediaType then only we can get Otherwise ContentType is null exception
		try {
			storeImage.setImageBytes(image.getBytes());//image has getBytes() and storing into the storeImage
		} catch (IOException e) {//compiler Known exception
			// TODO Auto-generated catch block
			e.printStackTrace();//it shows exact error line
		}//image has getBytes() and storing into the storeImage
		
		Image saved=imageRepo.save(storeImage); //everything stored in imageRepo is belonged to Image related
		ResponseStructure<String>structure=new ResponseStructure<>();
		
		return new ResponseEntity<ResponseStructure<String>>(
				structure.setStatus(HttpStatus.CREATED.value())
				         .setMessage("StoreImage uploaded successfully !!")
    /*String ->generic*/ .setData("/api/v1/images/"+saved.getImageId()),HttpStatus.CREATED);//giving link as a response so it will re
		}).orElseThrow(()->new StoreNotFoundByIdException("Failed to add image to store"));	
	}
	
	
	public ResponseEntity<byte[]> getImage(String imageId){//in mongoDb,imageId is String
		return imageRepo.findById(imageId).map(image->{//mapping the image Entity by imageRepo which has been updated
			return ResponseEntity.ok().contentType(MediaType.valueOf(image.getContentType()))//what format(jpeg,png)
					                  .contentLength(image.getImageBytes().length)//size of the image
					                  .body(image.getImageBytes());//byte[]-generic
		}).orElseThrow(()->new ImageNotFoundByIdException("Image is not found"));
		
	
		
	}

}
