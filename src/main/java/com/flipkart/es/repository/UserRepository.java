package com.flipkart.es.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flipkart.es.entity.User;
import com.flipkart.es.enums.UserRole;

public interface UserRepository extends JpaRepository<User, Integer>{

	boolean existsByUserEmail(String userEmail);

   boolean existsByUserEmailAndIsEmailVerified(String userEmail, boolean isEmailVerified);

    Optional<User> findByUsername(String username);

    List<User> findByIsEmailVerified(boolean isEmailVerified);

	Optional<User> findByUsernameAndUserRole(String username, UserRole seller);
	/*optional has many inbuild methods:
	 * Optional.of("Hello, World!") creates an Optional containing the value "Hello, World!".
       Optional.empty() creates an empty Optional.
       isPresent() checks if the Optional has a value.
       ifPresent() performs an action if the value is present.
       orElse() provides a default value if the Optional is empty.*/
	

}
