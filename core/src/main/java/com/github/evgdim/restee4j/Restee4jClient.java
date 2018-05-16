package com.github.evgdim.restee4j;

import java.io.IOException;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.github.evgdim.restee4j.decorator.BasicAuthenticationDecorator;
import com.github.evgdim.restee4j.decorator.EmptyDecorator;
import com.github.evgdim.restee4j.decorator.HttpClientDecorator;
import com.github.evgdim.restee4j.decorator.HystrixDecorator;

public class Restee4jClient {
	private final HttpClientDecorator decorator;
	
	private Restee4jClient(HttpClientDecorator decorator) {
		this.decorator = decorator;
	}
	
	public CloseableHttpResponse execute(HttpUriRequest request) throws IOException {
		return this.decorator.execute(request);
	}
	
	public static class Restee4jClientBuilder {
		private HttpClientDecorator decorator;
		private CloseableHttpClient httpClient;
		private Restee4jClientBuilder(CloseableHttpClient httpClient) {
			this.httpClient = httpClient;
		}
		
		public static Restee4jClientBuilder create(CloseableHttpClient httpClient, int connectTimeout, int connectionRequestTimeout, int socketTimeout) {
			return new Restee4jClientBuilder(httpClient);
		}
		
		public static Restee4jClientBuilder create(CloseableHttpClient httpClient) {
			return create(httpClient,3000, 3000, 3000);
		}
		
		public static Restee4jClientBuilder create() {
			RequestConfig requestConfig = RequestConfig.custom().
					setConnectTimeout(3000).
					setConnectionRequestTimeout(3000).
					setSocketTimeout(3000)
					.build();
			CloseableHttpClient httpClient = HttpClients.custom()
					.setDefaultRequestConfig(requestConfig)
					.build();
			return create(httpClient);
		}
		
		
		public Restee4jClientBuilder basicAuthentication(String username, String password) {
			if(this.decorator == null) {
				this.decorator = new BasicAuthenticationDecorator(httpClient, username, password);
			} else {
				BasicAuthenticationDecorator basicAuthenticationDecorator = new BasicAuthenticationDecorator(decorator, username, password);
				this.decorator = basicAuthenticationDecorator;
			}
			return this;
		}
		
		public Restee4jClientBuilder cirquitBreaker(String groupKey) {
			if(this.decorator == null) {
				this.decorator = new HystrixDecorator(httpClient, groupKey);
			} else {
				HystrixDecorator hystrixDecorator = new HystrixDecorator(decorator, groupKey);
				this.decorator = hystrixDecorator;
			}
			return this;
		}
		
		public Restee4jClient build() {
			if(decorator == null) {
				decorator = new EmptyDecorator(httpClient);
			}
			return new Restee4jClient(decorator);
		}
	}
}
