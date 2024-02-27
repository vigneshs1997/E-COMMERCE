package com.flipkart.es.entity;

import com.flipkart.es.enums.UserRole;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
@Getter
@Setter
@Table(name = "users")//table is created based on this name
@Inheritance(strategy = InheritanceType.JOINED)//what ever changes in parent, it effects in child class also=>here only we are giving every credentials 
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private String username;
    private String userEmail;
    private String userPassword;
    @Enumerated(EnumType.STRING)//it will provide 0,1,2 in database if we do not mention STRING
    private UserRole userRole;
    private boolean isEmailVerified=false;
    private boolean isDeleted=false;
}
