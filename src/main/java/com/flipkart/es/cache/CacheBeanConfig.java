package com.flipkart.es.cache;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.flipkart.es.entity.User;

@Configuration//it takes bean object
public class CacheBeanConfig {
    @Bean
	public CacheStrore<User> userCacheStrore(){
		return new CacheStrore<User>(Duration.ofMinutes(5));
		
	}
    @Bean
    public CacheStrore<String> otpCacheStore(){
		return new CacheStrore<String>(Duration.ofMinutes(5));
    	
    }
}
