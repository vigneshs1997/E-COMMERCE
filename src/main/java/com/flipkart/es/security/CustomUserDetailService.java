package com.flipkart.es.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.flipkart.es.entity.User;
import com.flipkart.es.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private UserRepository userRepository;//object injection

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//    	Optional<User> optional=userRepository.findByUsername(username);
//    	User user1=null;
//    	if (optional.isPresent())
//    		user1=optional.get();
//    	
//    	UserDetails userDetails=new CustomUserDetail(user1);
//    	return userDetails;
       return userRepository.findByUsername(username)
                .map(user -> new CustomUserDetail(user))
                .orElseThrow(() -> new UsernameNotFoundException("not found"));
    }

}
