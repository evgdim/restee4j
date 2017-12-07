package com.github.evgdim.restee4j.decorator;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;

import com.github.evgdim.restee4j.decorator.oauth.TokenData;
import com.github.evgdim.restee4j.decorator.oauth.TokenRequester;

public class OAuthDecorator extends HttpClientDecorator {
	private final TokenRequester tokenRequester;

	public OAuthDecorator(HttpClientDecorator child, TokenRequester tokenRequester) {
		super(child);
		if(child == null) {	throw new IllegalArgumentException("Child decorator cannot be null.");	}
		if(tokenRequester == null) {	throw new IllegalArgumentException("TokenRequester cannot be null.");	}
		this.tokenRequester = tokenRequester;
	}
	
	public OAuthDecorator(CloseableHttpClient httpClient, TokenRequester tokenRequester) {
		super(httpClient);
		if(httpClient == null) {throw new IllegalArgumentException("Http client cannot be null."); }
		if(tokenRequester == null) {throw new IllegalArgumentException("TokenRequester cannot be null.");	}
		this.tokenRequester = tokenRequester;
	}

	@Override
	public CloseableHttpResponse execute(HttpUriRequest request) throws IOException {
		TokenData tokenData = this.tokenRequester.requestToken(this.getHttpClient());
		request.setHeader("Authorization", "Bearer "+tokenData.getAccessToken());
		if(this.child != null) {
			return this.child.execute(request);
		}
		return this.httpClient.execute(request);
	}

}
