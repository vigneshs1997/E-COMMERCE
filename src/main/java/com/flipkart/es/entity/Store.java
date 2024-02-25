package com.flipkart.es.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
//getter and setter methods have arguements
@Getter
@Setter
@Builder
@Table(name="stores")//create table based on this name
@AllArgsConstructor//constructors accepts arguments
@NoArgsConstructor
public class Store {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//id value taken automatically
	private int storeId;
	private String storeName;
	private String logoLink;
	private String about;
	
	@OneToOne
	private Address address; //Store has a address
	
	
}
