package com.github.evgdim.restee4j;

import java.util.concurrent.TimeUnit;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

public class Restee4jClient {
	private final CloseableHttpClient httpClient;
	private final Cache<HttpUriRequest, CloseableHttpResponse> requestsCache;
	
	public Restee4jClient(CloseableHttpClient httpClient) {
		this.httpClient = httpClient;
		requestsCache = Caffeine.newBuilder()
			    .maximumSize(10_000)
			    .expireAfterWrite(5, TimeUnit.MINUTES)
			    //.refreshAfterWrite(1, TimeUnit.MINUTES)
			    .build();
	}
	
	public Restee4jClient() {
		this(HttpClients.createDefault());
	}
	
	public CloseableHttpResponse execute(HttpUriRequest request) {
		CloseableHttpResponse cachedResponse = requestsCache.get(request, req -> {
			try {
				return this.httpClient.execute(req);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
		return cachedResponse;
	}
}
