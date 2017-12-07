package com.github.evgdim.restee4j;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class Restee4jClient {
	private final CloseableHttpClient httpClient;
	
	public Restee4jClient(CloseableHttpClient httpClient) {
		this.httpClient = httpClient;
	}
	
	public Restee4jClient() {
		this(HttpClients.createDefault());
	}
	
	public CloseableHttpResponse execute(HttpUriRequest request) throws IOException {
		return this.httpClient.execute(request);
	}
	
	public CloseableHttpResponse executeWithHystrix(HttpUriRequest request) {
		return new HystrixHttpClientCommand(request, httpClient, request.getURI().toString()).execute();
	}
}
