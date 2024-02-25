package com.flipkart.es.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "sellers")


public class Seller extends User{//class to class is extend =>subclass of seller is getting all the properties of user
	
	@OneToOne//one seller has one store
	private Store store;//Seller has store and id of store is created in seller table
    
}
