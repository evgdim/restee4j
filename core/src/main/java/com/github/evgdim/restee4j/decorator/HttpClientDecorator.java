package com.github.evgdim.restee4j.decorator;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;

public abstract class HttpClientDecorator {
	protected HttpClientDecorator child;
	protected CloseableHttpClient httpClient;
	
	
	public HttpClientDecorator(HttpClientDecorator child) {
		this.child = child;
	}

	public HttpClientDecorator(CloseableHttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public abstract CloseableHttpResponse execute(HttpUriRequest request) throws IOException;

	public HttpClientDecorator getChild() {
		return child;
	}

	public CloseableHttpClient getHttpClient() {
		return httpClient;
	}
	
	
}
