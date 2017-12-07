package com.github.evgdim.restee4j.decorator;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;

public class GetRequestCachingDecorator extends HttpClientDecorator {

	public GetRequestCachingDecorator(HttpClientDecorator child) {
		super(child);
		if(child == null) {
			throw new IllegalArgumentException("Child decorator cannot be null.");
		}
	}
	
	public GetRequestCachingDecorator(CloseableHttpClient httpClient) {
		super(httpClient);
		if(httpClient == null) {
			throw new IllegalArgumentException("Http client cannot be null.");
		}
	}

	@Override
	public CloseableHttpResponse execute(HttpUriRequest request) throws IOException {
		if(request instanceof HttpGet) {
			//TODO
		}
		if(this.child != null) {
			return this.child.execute(request);
		}
		return this.httpClient.execute(request);
	}

}
