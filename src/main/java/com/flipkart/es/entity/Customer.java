package com.flipkart.es.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name="customers")
@Entity
@Getter
@Setter
public class Customer extends User{
	
	@OneToMany(mappedBy = "customer") //(bidirectional mapping),(it is  non owning side),(column will be created in owning side),(It can not be deleted easily)
	private List<Address> addresses;

}
