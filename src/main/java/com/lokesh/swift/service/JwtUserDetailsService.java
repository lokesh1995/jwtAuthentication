package com.lokesh.swift.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		if("lokesh".equals(username))
		{
			System.out.println("check");
			System.out.println(username);
			return new User(username, "$2a$10$nsHS7Zgj5/j1nOPUzeKKueMG.eFlGlTpWmGTShhHvEbykDX07Pt3q",new ArrayList<>());
		}
		else {
			throw new UsernameNotFoundException("User with "+username+" not found");
		}
		
	}

}
