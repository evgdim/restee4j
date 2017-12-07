package com.github.evgdim.restee4j.decorator;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;

public class EmptyDecorator extends HttpClientDecorator {

	public EmptyDecorator(HttpClientDecorator child) {
		super(child);
		if(child == null) {
			throw new IllegalArgumentException("Child decorator cannot be null.");
		}
	}
	
	public EmptyDecorator(CloseableHttpClient httpClient) {
		super(httpClient);
		if(httpClient == null) {
			throw new IllegalArgumentException("Http client cannot be null.");
		}
	}

	@Override
	public CloseableHttpResponse execute(HttpUriRequest request) throws IOException {
		if(this.child != null) {
			return this.child.execute(request);
		}
		return this.httpClient.execute(request);
	}

}
