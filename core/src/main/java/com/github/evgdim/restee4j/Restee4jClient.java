package com.github.evgdim.restee4j;

import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Restee4jClient {
	private final HttpClient httpClient;
	private final ObjectMapper mapper;
	
	public Restee4jClient(HttpClient httpClient, ObjectMapper mapper) {
		this.httpClient = httpClient;
		this.mapper = mapper;
	}
	
	public Restee4jClient() {
		this(new HttpClient(), new ObjectMapper());
	}
	
	public Response<?> getJson(String uri, Class<?> clazz){
		HttpMethod getMethod = new GetMethod(uri);
		int httpCode = -1;
		try {
			httpCode = this.httpClient.executeMethod(getMethod);
			InputStream responseBodyStream = getMethod.getResponseBodyAsStream();
			Object readValue = mapper.readValue(responseBodyStream, clazz);
			return new Response<>(httpCode, clazz.cast(readValue), null); 
		} catch (Exception e) {
			return new Response<String>(httpCode, e.getMessage(), null);
		} finally {
			getMethod.releaseConnection();
		}
	}
}
