package com.flipkart.es.entity;

import java.time.LocalDateTime;

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
@Builder//It allows us to create object
@AllArgsConstructor//it will create constructor with parameter
@NoArgsConstructor//it will create constructor without parameter
public class AccessToken {

	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private long tokenId;
	private String token;
	private boolean isBlocked;
	private LocalDateTime accessTokenExpiration;
	
	@ManyToOne
	private User user;
	
}
