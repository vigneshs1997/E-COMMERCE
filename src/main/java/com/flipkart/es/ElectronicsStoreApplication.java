
package com.flipkart.es;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling//for setting particular task at particular time //=>( enabling Scheduledjobs class from util)
@SpringBootApplication//=>@EnableConfiguration,@Configuration and @ComponentScan //=>
@EnableAsync//=>it makes asynchronous to do multithread
public class ElectronicsStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElectronicsStoreApplication.class, args);//it will create spring container 
	}

}
/*
 * 
 * 
 */