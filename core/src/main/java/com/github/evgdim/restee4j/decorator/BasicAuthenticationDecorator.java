package com.github.evgdim.restee4j.decorator;

import java.io.IOException;
import java.util.Base64;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;

public class BasicAuthenticationDecorator extends HttpClientDecorator {
	private final String basicAuthHeaderValue;

	public BasicAuthenticationDecorator(HttpClientDecorator child, String username, String password) {
		super(child);
		if(child == null || StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			throw new IllegalArgumentException("Child decorator, username or password cannot be null.");
		}
		String userPass = username + ":" + password;
		this.basicAuthHeaderValue = Base64.getEncoder().encodeToString(userPass.getBytes());
	}
	
	public BasicAuthenticationDecorator(CloseableHttpClient httpClient, String username, String password) {
		super(httpClient);
		if(httpClient == null || StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			throw new IllegalArgumentException("Child decorator, username or password cannot be null.");
		}
		String userPass = username + ":" + password;
		this.basicAuthHeaderValue = Base64.getEncoder().encodeToString(userPass.getBytes());
	}

	@Override
	public CloseableHttpResponse execute(HttpUriRequest request) throws IOException {
		Header authorizationHeader = new BasicHeader("Authorization", "Basic " + this.basicAuthHeaderValue);
		request.addHeader(authorizationHeader);
		if(this.child != null) {
			return this.child.execute(request);
		}
		return this.httpClient.execute(request);
	}

}
