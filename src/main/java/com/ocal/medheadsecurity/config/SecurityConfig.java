package com.ocal.medheadsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity	
public class SecurityConfig {

	//private JwtAuthEntryPoint authEntryPoint;

	@Autowired 
	public SecurityConfig(){//JwtAuthEntryPoint authEntryPoint) {
		//this.authEntryPoint = authEntryPoint;
	} 
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
        .cors().and()
        .csrf().disable()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeHttpRequests()
        .requestMatchers("/auth/**").permitAll();
		http.headers().frameOptions().disable();
		return http.build();  
    }
    
    @Bean
    public AuthenticationManager authenticationManager(
    		AuthenticationConfiguration authenticationConfiguration) 
    				throws Exception{
    	return authenticationConfiguration.getAuthenticationManager();
    }
    
    @Bean
    PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
}