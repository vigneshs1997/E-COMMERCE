package com.flipkart.es.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flipkart.es.entity.AccessToken;

public interface AccessTokenRepo extends JpaRepository<AccessToken, Long> {

}
