package com.github.evgdim.restee4j;

import java.io.IOException;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.github.evgdim.restee4j.decorator.BasicAuthenticationDecorator;
import com.github.evgdim.restee4j.decorator.HttpClientDecorator;

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
		
		public static Restee4jClientBuilder create(int connectTimeout, int connectionRequestTimeout, int socketTimeout) {
			RequestConfig requestConfig = RequestConfig.custom().
					  setConnectTimeout(connectTimeout).
					  setConnectionRequestTimeout(connectionRequestTimeout).
					  setSocketTimeout(socketTimeout)
					  .build();
			CloseableHttpClient httpClient = HttpClients.custom()
					.setDefaultRequestConfig(requestConfig)
					.build();
			return new Restee4jClientBuilder(httpClient);
		}
		
		public static Restee4jClientBuilder create() {
			return create(3000, 3000, 3000);
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
		
		public Restee4jClient build() {
			return new Restee4jClient(decorator);
		}
	}
}
