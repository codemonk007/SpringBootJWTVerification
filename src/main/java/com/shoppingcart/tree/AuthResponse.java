package com.shoppingcart.tree;

public class AuthResponse {

	public AuthResponse(String jwt) {
		super();
		this.jwt = jwt;
	}
	private final String jwt;

	public String getJwt() {
		return jwt;
	}
}
