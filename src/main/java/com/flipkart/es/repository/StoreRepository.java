package com.flipkart.es.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flipkart.es.entity.Store;
                                                       
public interface StoreRepository extends JpaRepository<Store, Integer> {

	

}
/*
  JpaRepository is a part of the Spring Data JPA framework, which is an extension of the Spring Data project.
  Spring Data JPA aims to simplify data access in Spring-based applications,
  particularly when dealing with Java Persistence API (JPA) and relational databases.

  The JpaRepository interface is an interface provided by Spring Data JPA that extends the CrudRepository interface.
  It provides CRUD (Create, Read, Update, Delete) operations for working with entities. By extending JpaRepository,
  you get a set of generic methods for common database operations, such as saving an entity,
  finding an entity by its primary key, deleting an entity, and more.
*/
