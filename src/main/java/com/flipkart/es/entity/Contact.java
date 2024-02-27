package com.flipkart.es.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flipkart.es.enums.Priority;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int contactId;
	private String contactName;
	private long contactNumber;
	private Priority priority;
	
	@JsonIgnore//it will ignore address coming in response of contact
	@ManyToOne
    private Address address;
}
