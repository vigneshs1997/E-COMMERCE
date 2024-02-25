package com.flipkart.es.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.AllArgsConstructor;
//formLogin(Customizer.withDefaults() OR httpBasic(Customizer.withDefaults())

@AllArgsConstructor//insteaded of using @Autowire=>
@Configuration//it tells the spring we create bean object here
@EnableWebSecurity//it is used for security purpose
@EnableMethodSecurity//it is used to enable @PreAuthorize in controller class
public class SecurityConfig {

    private CustomUserDetailService customUserDetailService;
    private JwtFilter jwtFilter;

    @Bean//creating object and return also=>Bean is created in configuration class only=> if we give @Bean,spring will handle that object 
    PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder(12);
    }
    
    @Bean //Bean is created in configuration class only
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.requestMatchers("/**").permitAll()
                        .anyRequest().authenticated())
                        .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .authenticationProvider(authenticationProvider())
                        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                        .build();
//               
    }

    @Bean
    AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailService);
        provider.setPasswordEncoder(getPasswordEncoder());
        return provider;
    }
    
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager(); //it throws an declaration
    	
    }

}
