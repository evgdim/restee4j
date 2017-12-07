package com.github.evgdim.restee4j;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class HystrixHttpClientCommand extends HystrixCommand<CloseableHttpResponse> {
	private final HttpUriRequest request;
	private final CloseableHttpClient httpClient;
	
	public HystrixHttpClientCommand(HttpUriRequest request, CloseableHttpClient httpClient, String groupKey) {
		super(HystrixCommandGroupKey.Factory.asKey(groupKey));
        this.request = request;
        this.httpClient = httpClient;
	}
	
	@Override
	protected CloseableHttpResponse run() throws Exception {
		return this.httpClient.execute(request);
	}

}
