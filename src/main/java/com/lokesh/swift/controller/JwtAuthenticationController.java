package com.lokesh.swift.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lokesh.swift.config.JwtTokenUtil;
import com.lokesh.swift.model.JwtRequest;
import com.lokesh.swift.model.JwtResponse;
import com.lokesh.swift.service.JwtUserDetailsService;

@RestController
@CrossOrigin
public class JwtAuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;
	
	@PostMapping(value = "/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest jwtRequest) throws Exception
	{
		System.out.println(jwtRequest.getUserName());
		System.out.println(jwtRequest.getPassword());
		authenticate(jwtRequest.getUserName(), jwtRequest.getPassword());
		
		final UserDetails userDetails= jwtUserDetailsService.loadUserByUsername(jwtRequest.getUserName());
		
		final String token = jwtTokenUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(new JwtResponse(token));
	}
	
	private void authenticate(String username,String password) throws Exception
	{
		try {
			
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			
		} catch (DisabledException e) {
			
			throw new Exception("USER_DISABLED",e);
		}
		
		catch (BadCredentialsException e) {
			
			throw new Exception("BAD_CREDENTIALS",e);
		}
		
	}

}
