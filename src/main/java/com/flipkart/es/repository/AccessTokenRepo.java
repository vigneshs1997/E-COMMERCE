package com.flipkart.es.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flipkart.es.entity.AccessToken;
import com.flipkart.es.entity.User;

public interface AccessTokenRepo extends JpaRepository<AccessToken, Long> {

	Optional<AccessToken> findByToken(String at);

	Optional<AccessToken> findByTokenAndIsBlocked(String token, boolean isBlocked);
	
	List<AccessToken> findAllByUserAndIsBlocked(User user, boolean b);

	List<AccessToken> findAllByUserAndIsBlockedAndTokenNot(User user, boolean b, String accessToken);
	
	

	
	
		
	

}
