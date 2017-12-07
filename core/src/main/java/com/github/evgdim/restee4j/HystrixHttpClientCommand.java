package com.github.evgdim.restee4j;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;

import com.github.evgdim.restee4j.decorator.HttpClientDecorator;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class HystrixHttpClientCommand extends HystrixCommand<CloseableHttpResponse> {
	private final HttpUriRequest request;
	private final HttpClientDecorator decorator;
	private final CloseableHttpClient httpClient;
	
	public HystrixHttpClientCommand(HttpUriRequest request, HttpClientDecorator decorator, String groupKey) {
		super(HystrixCommandGroupKey.Factory.asKey(groupKey));
        this.request = request;
        this.decorator = decorator;
        this.httpClient = null;
	}
	public HystrixHttpClientCommand(HttpUriRequest request, CloseableHttpClient httpClient, String groupKey) {
		super(HystrixCommandGroupKey.Factory.asKey(groupKey));
        this.request = request;
        this.decorator = null;
        this.httpClient = httpClient;
	}
	
	@Override
	protected CloseableHttpResponse run() throws Exception {
		if(decorator != null) {
			return this.decorator.execute(request);
		}
		return this.httpClient.execute(request);
	}

}
