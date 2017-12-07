package com.github.evgdim.restee4j.decorator.oauth;

public class TokenData {
	private String accessToken;
	private String refreshToken;
	private Integer expiresIn;
	
	public TokenData(String accessToken, String refreshToken, Integer expiresIn) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.expiresIn = expiresIn;
	}
	
	public String getAccessToken() {
		return accessToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public Integer getExpiresIn() {
		return expiresIn;
	}
}
