package com.flipkart.es.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flipkart.es.entity.AccessToken;
import com.flipkart.es.entity.RefreshToken;
import com.flipkart.es.requestdto.AuthRequest;
import com.flipkart.es.requestdto.OtpModel;
import com.flipkart.es.requestdto.UserRequest;
import com.flipkart.es.responsedto.AuthResponse;
import com.flipkart.es.responsedto.UserResponse;
import com.flipkart.es.service.AuthService;
import com.flipkart.es.util.ResponseStructure;
import com.flipkart.es.util.SimpleResponseStructure;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@RestController//it contains @controller and (@responsebody=> it gives exact data mentioned by us to client)
@AllArgsConstructor//it is used to constructor for the reference variable 
@RequestMapping("/api/v1")//it is general http methods=> like versions(updates) whatsapp=>(If we updates, we will give version 2) =>global url
@CrossOrigin(allowCredentials = "true",origins="http://localhost:5173/")//=>connecting backEnd to frontEnd
public class AuthController {
	
	private AuthService authService;//@AllArgsConstructor
	
	
	@PostMapping("/users/register")
	public ResponseEntity<ResponseStructure<UserResponse>> registerUser(@RequestBody UserRequest userRequest) {
		return authService.registerUser(userRequest);
	}
	
	@PostMapping("/verify-otp")
	public ResponseEntity<String> verifyOTP(@RequestBody OtpModel otp){
		return authService.verifyOTP(otp);
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<ResponseStructure<AuthResponse>> login(@RequestBody AuthRequest authRequest,HttpServletResponse response){
		return authService.login(authRequest, response);//if cookie is there,user does not want to login
	}
	@PreAuthorize(value="hasAuthority('SELLER') or hasAuthority('CUSTOMER')")//@PreAuthorize is the method level security in order to enable this we  need to give annotation in controller 
	@PutMapping("/logout")
	public ResponseEntity<ResponseStructure<String>> logout(@CookieValue(value="at",required = false)String at
			,@CookieValue(value = "rt",required = false) String rt,HttpServletResponse response){
		return authService.logout(at,rt, response);
	}
	@PreAuthorize(value="hasAuthority('SELLER') or hasAuthority('CUSTOMER')")
	@PutMapping("/revokeAllDevice")
	public ResponseEntity<ResponseStructure<String>>revokeAllDevice(HttpServletResponse response)
	{ /*if my account is used by other users , i need to logout*/
		return authService.revokeAllDevice(response);
	}
	
	@PreAuthorize(value="hasAuthority('SELLER') or hasAuthority('CUSTOMER')")
	@PutMapping("/revokeOtherDevice")
	public ResponseEntity<ResponseStructure<String>> revokeOtherDevice(@CookieValue(value="at",required = false)String accessToken
			,@CookieValue(value = "rt",required = false) String refreshToken,HttpServletResponse response){
		return authService.logout(accessToken,refreshToken, response);
	}

	@PutMapping("/refresh")
	public ResponseEntity<SimpleResponseStructure> refreshLogin(
			@CookieValue(name = "at", required = false) String accessToken,
			@CookieValue(name = "rt", required = false) String refreshToken, HttpServletResponse response) {
		return authService.refreshLogin(accessToken, refreshToken, response);
	}
	/*if generate new access token, we need to refresh refresh token*/
	/*if generate new refresh token, we need to redirect to login page*/
}
