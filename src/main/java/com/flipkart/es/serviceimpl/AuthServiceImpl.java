package com.flipkart.es.serviceimpl;

import java.util.Date;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.flipkart.es.cache.CacheStrore;
import com.flipkart.es.entity.Customer;
import com.flipkart.es.entity.Seller;
import com.flipkart.es.entity.User;
import com.flipkart.es.enums.UserRole;
import com.flipkart.es.exception.InvalidUserRoleException;
import com.flipkart.es.exception.UserVerifiedException;
import com.flipkart.es.repository.CustomerRepository;
import com.flipkart.es.repository.SellerRepository;
import com.flipkart.es.repository.UserRepository;
import com.flipkart.es.requestdto.OtpModel;
import com.flipkart.es.requestdto.UserRequest;
import com.flipkart.es.responsedto.UserResponse;
import com.flipkart.es.service.AuthService;
import com.flipkart.es.util.MessageStructure;
import com.flipkart.es.util.ResponseEntityProxy;
import com.flipkart.es.util.ResponseStructure;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j //=>
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    //private methods should be created on top because they can not be accessed if they are placed at bottom
	
	private UserRepository userRepository;
	private SellerRepository sellerRepository;
	private CustomerRepository customerRepository;
	private PasswordEncoder passwordEncoder;
	private ResponseStructure<UserResponse> structure;
	private CacheStrore<String> otpCacheStore;
	private CacheStrore<User> userCacheStore;
	private JavaMailSender javaMailSender;

	@SuppressWarnings("unchecked")
	private <T extends User> T mapToRespectiveChild(UserRequest userRequest) {

		User user = null;
		switch (UserRole.valueOf(userRequest.getUserRole().toUpperCase())) {
			case SELLER -> {
				user = new Seller();
			}
			case CUSTOMER -> {
				user = new Customer();
			}
			default -> throw new InvalidUserRoleException("User not found with the specified role");
		}

		user.setUsername(userRequest.getUserEmail().split("@")[0].toString());
		user.setUserEmail(userRequest.getUserEmail());
		user.setUserPassword(passwordEncoder.encode(userRequest.getUserPassword()));
		user.setUserRole(UserRole.valueOf(userRequest.getUserRole().toUpperCase()));
		user.setEmailVerified(false);
		user.setDeleted(false);

		return (T) user;

	}



	public User saveUser(User user) {

		if (user.getUserRole().equals(UserRole.SELLER)) {
			Seller seller = (Seller) user;
			return sellerRepository.save(seller);
		} else {
			Customer customer = (Customer) user;
			return customerRepository.save(customer);
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> registerUser(UserRequest userRequest) {

	
		
		if(userRepository.existsByUserEmail(userRequest.getUserEmail())) 
			throw new UserVerifiedException("User is already present with the given email Id ");
		
	    String OTP = generateOTP();
		User user = mapToRespectiveChild(userRequest);
		userCacheStore.add(userRequest.getUserEmail(),user);
		otpCacheStore.add(userRequest.getUserEmail(),OTP);
		
		try {
			sendOtpToMail(user,OTP);
		} catch (MessagingException e) {
			
			log.error("The email address does not exist");;
		}
		
		return new ResponseEntity<ResponseStructure<UserResponse>>(structure.setStatus(HttpStatus.ACCEPTED.value())
				.setMessage("Please verify through OTP :"+OTP)
				.setData(mapToUserResponse(user)),HttpStatus.ACCEPTED);
	}
	
	@Override
	public ResponseEntity<String> verifyOTP(OtpModel otpModel){
		User user= userCacheStore.get(otpModel.getEmail());
		String otp=otpCacheStore.get(otpModel.getEmail());
		
		if(otp == null) throw new RuntimeException("OTP expired");
		if(user == null) throw new RuntimeException("Registeration session expired");
		if(!otp.equals(otpModel.getOtp())) throw new RuntimeException("Invalid OTP");
		
		user.setEmailVerified(true);
		userRepository.save(user);
		return new ResponseEntity<String>("Registration successfull",HttpStatus.CREATED) ;
		
		
	}
	
	private void sendOtpToMail(User user,String otp) throws MessagingException {
		sendMail(MessageStructure.builder()
		.to(user.getUserEmail())
		.subject("Complete your Registration To Flipkart")
		.sentDate(new Date())
		.text(
				"hey, "+user.getUsername()
				+" Good to see you intrested in flipkart,"
				+" Complete your registration using the OTP <br>"
				+"<h1>"+otp+"</h1><br>"
				+"Note: the OTP expires in 1 minute"
				+"<br><br>"
				+"with best regards<br>"
				+"Flipkart").build());
	}
	private void sendResponseMail(User user) throws MessagingException {
		MessageStructure.builder()
		.to(user.getUserEmail())
		.subject("Complete your Registration To Flipkart")
		.sentDate(new Date())
		.text(
				"hey, "+user.getUsername()
				+" Good to see you intrested in flipkart,"
				+"You have successfully registered"
				+"<br><br>"
				+"with best regards<br>"
				+"Flipkart").build();
	}
	@Async
	private void sendMail(MessageStructure message) throws MessagingException {
		MimeMessage mimeMessage=javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		helper.setTo(message.getTo());
		helper.setSubject(message.getSubject());
		helper.setSentDate(message.getSentDate());
		helper.setText(message.getText(), true);
		javaMailSender.send(mimeMessage);
	}
	
	private String generateOTP() {
		return String.valueOf(new Random().nextInt(100000, 999999));
	}
	
	private UserResponse mapToUserResponse(User user) {

		return UserResponse.builder()
				.userId(user.getUserId())
				.userEmail(user.getUserEmail())
				.username(user.getUsername())
				.userRole(user.getUserRole())
				.isDeleted(user.isDeleted())
				.isEmailVerified(user.isEmailVerified())
				.build();

	}








}
