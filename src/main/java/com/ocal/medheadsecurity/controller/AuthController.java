package com.ocal.medheadsecurity.controller;



import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ocal.medheadsecurity.model.Role;
import com.ocal.medheadsecurity.model.UserEntity;
import com.ocal.medheadsecurity.repository.RoleRepository;
import com.ocal.medheadsecurity.repository.UserRepository;

import com.ocal.medheadsecurity.dto.AuthResponseDTO;
import com.ocal.medheadsecurity.dto.LoginDTO;
import com.ocal.medheadsecurity.dto.RegisterDTO;

import com.ocal.medheadsecurity.config.JWTGenerator;

@RestController
@RequestMapping("/auth")
public class AuthController {
	private AuthenticationManager  authenticationManager;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	private JWTGenerator jwtGenerator;
	
	
	@Autowired 
	public AuthController(
			AuthenticationManager authenticationManager,
			UserRepository userRepository,
			RoleRepository roleRepository,
			PasswordEncoder passwordEncoder,
			JWTGenerator jwtGenerator) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository  = roleRepository;
		this.passwordEncoder  = passwordEncoder;
		this.jwtGenerator = jwtGenerator;
	}
	
	@PostMapping("login")
	@CrossOrigin(origins = "*")
    @ResponseBody
	public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDto){
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginDto.getUsername(),
						loginDto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtGenerator.generateToken(authentication);
		return new ResponseEntity<>(new AuthResponseDTO(token),HttpStatus.OK);
	}
	
	
	@PostMapping("register")
	@CrossOrigin(origins = "*")
    @ResponseBody
	public ResponseEntity<String> register(@RequestBody RegisterDTO registerDto){
		if(userRepository.existsByUsername(registerDto.getUsername())){
			return new ResponseEntity<>("Username is taken !", HttpStatus.BAD_REQUEST);
		}
		
		UserEntity user = new UserEntity();
		user.setUsername(registerDto.getUsername());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		
		Role roles = roleRepository.findByName("USER").get();
		user.setRoles(Collections.singletonList(roles));
		
		userRepository.save(user);
		
		return new ResponseEntity<>("User registered success!",HttpStatus.OK);
	}
}
