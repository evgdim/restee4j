package com.github.evgdim.restee4j;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Test;

public class AppTest {
	@Test
	public void testHttpClient() throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = mock(CloseableHttpClient.class);
		CloseableHttpResponse httpResponse = mock(CloseableHttpResponse.class);
		StatusLine statusLine = mock(StatusLine.class);
		
		when(httpResponse.getStatusLine()).thenReturn(statusLine);
		when(statusLine.getStatusCode()).thenReturn(200).thenReturn(500); 
		when(httpClient.execute(anyObject())).thenReturn(httpResponse);
		
		assertEquals(200, httpClient.execute(new HttpGet()).getStatusLine().getStatusCode());
		assertEquals(500, httpClient.execute(new HttpGet()).getStatusLine().getStatusCode());
	}
	
	@Test
	public void testBuild() throws ClientProtocolException, IOException {
		Restee4jClient restee4jClient = Restee4jClient.Restee4jClientBuilder.create(10_000, 10_000, 10_000)
			.basicAuthentication("asd", "asd")
			.cirquitBreaker("test")
			.build();
		try {
			CloseableHttpResponse response = restee4jClient.execute(new HttpGet("http://www.google.bga"));
			System.out.println(response);
		} catch (Exception e) {
			System.out.println(e);
		}
		try {
			CloseableHttpResponse response = restee4jClient.execute(new HttpGet("http://www.google.bga"));
			System.out.println(response);
		} catch (Exception e) {
			System.out.println(e);
		}
		try {
			CloseableHttpResponse response = restee4jClient.execute(new HttpGet("http://www.google.bga"));
			System.out.println(response);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	@Test
	public void testBuildEmpty() throws ClientProtocolException, IOException {
		Restee4jClient restee4jClient = Restee4jClient.Restee4jClientBuilder.create(10_000, 10_000, 10_000)
			.build();
			CloseableHttpResponse response = restee4jClient.execute(new HttpGet("http://www.google.bg"));	
			System.out.println(response);
	}
}
