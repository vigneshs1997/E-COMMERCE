package com.flipkart.es.requestdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequest {
	private String streetAddress;
	private String streetAddressAdditional;
	private String city;
	private String state;
	private String country;
	private int pinCode; 
	private String addressType; //this is enum but for frontEnd we need to give dataType as String

}
