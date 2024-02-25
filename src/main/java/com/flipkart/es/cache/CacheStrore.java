package com.flipkart.es.cache;

import java.time.Duration;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;


            //generic class
public class CacheStrore<T> { //Cache memory is used to store otp for some period of time

	//cache object creation 
	private Cache<String,T> cache;
	  
	public  CacheStrore(Duration expiry) {   //constructor
		this.cache=CacheBuilder.newBuilder()
				.expireAfterWrite(expiry)
				.concurrencyLevel(Runtime.getRuntime()
						.availableProcessors()).build();
	}
	
	public void add(String key,T value) {
		cache.put(key,value);
	}
	
	public T get(String key) {
		return cache.getIfPresent(key);
		
	}
	public void remove(String key) {
		cache.invalidate(key);
	}
}
