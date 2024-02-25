package com.flipkart.es.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flipkart.es.entity.RefreshToken;
import com.flipkart.es.entity.User;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken,Long> {

	Optional<RefreshToken> findByToken(String rt);
	
	List<RefreshToken> findAllByUserAndIsBlocked(User user, boolean b);

	List<RefreshToken> findAllByUserAndIsBlockedAndTokenNot(User user, boolean b, String refreshToken);

	

}
