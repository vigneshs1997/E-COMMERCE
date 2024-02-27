package com.flipkart.es.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.flipkart.es.entity.Image;

public interface ImageRepository extends MongoRepository<Image, String>{

}
