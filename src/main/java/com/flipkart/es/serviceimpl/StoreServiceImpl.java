package com.flipkart.es.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.flipkart.es.entity.Seller;
import com.flipkart.es.entity.Store;
import com.flipkart.es.enums.UserRole;
import com.flipkart.es.exception.IllegalRequestException;
import com.flipkart.es.exception.NoStoredDataExistsException;
import com.flipkart.es.exception.SellerNotFoundByIdException;
import com.flipkart.es.exception.StoreAlreadyExistWithSellerException;
import com.flipkart.es.exception.StoreNotFoundByIdException;
import com.flipkart.es.exception.UserNotFoundException;
import com.flipkart.es.repository.SellerRepository;
import com.flipkart.es.repository.StoreRepository;
import com.flipkart.es.repository.UserRepository;
import com.flipkart.es.requestdto.StoreRequest;
import com.flipkart.es.responsedto.StoreResponse;
import com.flipkart.es.service.StoreService;
import com.flipkart.es.util.ResponseStructure;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Service//it is a serviceImplimentation layer
@AllArgsConstructor
public class StoreServiceImpl implements StoreService{
	//below variables are used for storing in that variable objects
	private StoreRepository storeRepo;//handling with store data base
	private ResponseStructure<StoreResponse>response;
	private UserRepository userRepo;//handling with user data base
	private SellerRepository sellerRepo;//handling with seller data base
	
/*============================================================addStore==================================================================================*/	
	@Override
	public ResponseEntity<ResponseStructure<StoreResponse>> addStore(StoreRequest request) {
	
		//getting username after seller who is registered  is logging first(telling user to login)
	 String username=SecurityContextHolder.getContext().getAuthentication().getName(); //datas is taken after login
	                                                                                   
	 if(username == null) throw new UsernameNotFoundException("Failed to Add Store");//if user not there it will throw the exception
	 
	  //if user is there, it will go next step	  (username,enumtype SELLER)
		return userRepo.findByUsernameAndUserRole(username,UserRole.SELLER).map(user->{
			
		Seller seller=(Seller)user;//super class object storing subclass reference type(DownCasting to seller)
		Store store  = mapToStore(request);//mapping the request to the store
		
		if(seller.getStore() != null) throw new StoreAlreadyExistWithSellerException("Failed to add store");//if sell has store, throw an exception
		/*(if seller does not have store,do the below operation)*/
		store=storeRepo.save(store); //create,read,update,delete,save are present  in JPA repository 
		
		seller.setStore(store);
		
		return new ResponseEntity<ResponseStructure<StoreResponse>>(
				response.setStatus(HttpStatus.CREATED.value())
				         .setMessage("Store Data Uploaded Sucessfully")
				         .setData(mapToStoreResponse(store)),HttpStatus.CREATED);
		
		}).orElseThrow(()->new IllegalRequestException("user not valid to create store"));//if we can not find username and userRole,throw an exception
	}
/*============================================================updateStore==================================================================================*/
	@Override
	public ResponseEntity<ResponseStructure<StoreResponse>> updateStore(@Valid StoreRequest request, int storeId) {
		String username=SecurityContextHolder.getContext().getAuthentication().getName();//datas is taken after login(after validation)
		if(username==null) throw new UserNotFoundException("Failed to update store");
		return storeRepo.findById(storeId).map(store->{//Store store
			                                         Store updatedStore=mapToStore(request);//calling mapping method and updating new data
			                                         updatedStore.setStoreId(store.getStoreId());//setting id in updatedStore
			                                         storeRepo.save(updatedStore);
			                                         
			                                         return new ResponseEntity<ResponseStructure<StoreResponse>>(
			                                        		 response.setStatus(HttpStatus.OK.value())
			                                        		         .setData(mapToStoreResponse(updatedStore))
			                                        		         .setMessage("Store Data updated Successfully"),HttpStatus.OK);
			
		}).orElseThrow(()->new StoreNotFoundByIdException("Failed to update the Store"));
	}
/*============================================================fetchAllStores============================================================================*/
	@Override
	public ResponseEntity<ResponseStructure<List<StoreResponse>>> fetchAllStores() {//it will return list of StoreResponse
	      List<Store> storeList=storeRepo.findAll();
	      if(!storeList.isEmpty())//isEmpty() is a inbuildMethod
	      {
	    	  List<StoreResponse>responseList=new ArrayList<>(); //creating Array object and it will accept StoreResponse
	    	  for(Store store:storeList)
	    		  responseList.add(mapToStoreResponse(store));//adding mapped response data into ArrayList
	    	 ResponseStructure<List<StoreResponse>> structure=new ResponseStructure<>();//creating object of responseStructure class which is another class
	    	 
	    	  return new ResponseEntity<ResponseStructure<List<StoreResponse>>>(//return to ResponseStructure class
	    			  
	    			  structure.setStatus(HttpStatus.FOUND.value())
	    			           .setMessage("Store list found")
	    			           .setData(responseList),HttpStatus.FOUND);
	      }throw new  NoStoredDataExistsException("Failed to fetch stored data");	
	}
/*============================================================fetchStoreById============================================================================*/
	@Override
	public ResponseEntity<ResponseStructure<StoreResponse>> fetchStoreById(int storeId) {
		
		return storeRepo.findById(storeId).map(store ->{//Store store
			return new ResponseEntity<ResponseStructure<StoreResponse>>(
					response.setStatus(HttpStatus.FOUND.value())
					        .setMessage("Store information fetched successfully for"+store.getStoreName())
					        .setData(mapToStoreResponse(store)),HttpStatus.FOUND);
		}).orElseThrow(()-> new StoreNotFoundByIdException("Failed to fetch store by ID"));
	}
/*============================================================fetchStoreBySeller========================================================================*/
	@Override
	public ResponseEntity<ResponseStructure<StoreResponse>> fetchStoreBySeller(int sellerId) {
		return sellerRepo.findById(sellerId).map(seller->{//Seller seller
		       Store store=seller.getStore();
		  if(store==null)throw new NoStoredDataExistsException("No Store for Seller"+seller.getUsername());
		  return new ResponseEntity<ResponseStructure<StoreResponse>>(
				  response.setStatus(HttpStatus.FOUND.value())
				          .setMessage("Store information found by seller")
				          .setData(mapToStoreResponse(store)),HttpStatus.FOUND);
		}).orElseThrow(()->new SellerNotFoundByIdException("Failed to fetch store"));
	}
	
/*===================================================================Mapping============================================================================*/
	private Store mapToStore(@Valid StoreRequest request) {//mapping request to the store Entity
		return Store.builder()
				.storeName(request.getStoreName())
				.about(request.getAbout())
				.build();
		
	}
	private StoreResponse mapToStoreResponse(Store store) {//mapping store Entity to StoreResponse
		return StoreResponse.builder().storeId(store.getStoreId())
				                      .storeName(store.getStoreName())
				                      .about(store.getAbout())
				                      .build();
	}
	
	

}
