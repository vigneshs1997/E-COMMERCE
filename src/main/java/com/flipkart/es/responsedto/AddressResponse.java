package com.flipkart.es.responsedto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder//(for mapping purpose)
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse {
	private int addressId;
	private String streetAddress;
	private String streetAddressAdditional;
	private String city;
	private String state;
	private String country;
	private int pinCode; 
	private String addressType; //this is enum but for frontEnd we need to give dataType as String
}
