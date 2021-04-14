package com.shoppingcart.tree;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Demo {

@Autowired
public AuthenticationManager _AuthenticationManager;

@Autowired
public MyUserDetailsService _MyUserDetailsService;

@Autowired
private jwt _jwt;

@RequestMapping("/getDemo")	
public String getSomeData(){
	return "Hi there";
}
	
@RequestMapping(value = "/auth" ,method = RequestMethod.POST)	
public ResponseEntity<?> auth(@RequestBody AuthRequest _AuthRequest){
	_AuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken( _AuthRequest.getUsername(),_AuthRequest.getPassword()));
	
	final UserDetails _UserDetails = _MyUserDetailsService.loadUserByUsername(_AuthRequest.getUsername());
	final String jwt = _jwt.generateToken(_UserDetails);
	return ResponseEntity.ok(new AuthResponse(jwt));
}
}
