package com.github.evgdim.restee4j.decorator;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;

import com.github.evgdim.restee4j.HystrixHttpClientCommand;

public class HystrixDecorator extends HttpClientDecorator {
	private final String groupKey; 

	public HystrixDecorator(HttpClientDecorator child, String groupKey) {
		super(child);
		if(child == null) {
			throw new IllegalArgumentException("Child decorator cannot be null.");
		}
		this.groupKey = groupKey;
	}
	
	public HystrixDecorator(CloseableHttpClient httpClient, String groupKey) {
		super(httpClient);
		if(httpClient == null) {
			throw new IllegalArgumentException("Http client cannot be null.");
		}
		this.groupKey = groupKey;
	}

	@Override
	public CloseableHttpResponse execute(HttpUriRequest request) throws IOException {
		if(this.child != null) {
			HystrixHttpClientCommand command = new HystrixHttpClientCommand(request, this.child, this.groupKey);
			return command.execute();
		}
		HystrixHttpClientCommand command = new HystrixHttpClientCommand(request, this.httpClient, this.groupKey);
		return command.execute();
	}

}
