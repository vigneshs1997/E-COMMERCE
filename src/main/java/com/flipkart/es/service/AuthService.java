package com.flipkart.es.service;

import org.springframework.http.ResponseEntity;


import com.flipkart.es.requestdto.AuthRequest;
import com.flipkart.es.requestdto.OtpModel;
import com.flipkart.es.requestdto.UserRequest;
import com.flipkart.es.responsedto.AuthResponse;
import com.flipkart.es.responsedto.UserResponse;
import com.flipkart.es.util.ResponseStructure;
import com.flipkart.es.util.SimpleResponseStructure;

import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

	ResponseEntity<ResponseStructure<UserResponse>> registerUser(UserRequest userRequest);

	ResponseEntity<String> verifyOTP(OtpModel otp);

	ResponseEntity<ResponseStructure<AuthResponse>> login(AuthRequest authRequest,HttpServletResponse response);

	ResponseEntity<ResponseStructure<String>> logout(String at, String rt, HttpServletResponse response);
   
	ResponseEntity<ResponseStructure<String>> revokeAllDevice(HttpServletResponse response);

	

	ResponseEntity<ResponseStructure<String>> revokeOtherDevice(String refreshToken, String accessToken,
			HttpServletResponse response);

	ResponseEntity<SimpleResponseStructure> refreshLogin(String accessToken, String refreshToken, HttpServletResponse response);

	
	
//clt+A+I
}
