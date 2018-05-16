package com.github.evgdim.restee4j;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

public class EmptyDecoratorTests {
	private CloseableHttpClient httpClient = mock(CloseableHttpClient.class);
	private CloseableHttpResponse httpResponse = mock(CloseableHttpResponse.class);
	private StatusLine statusLine = mock(StatusLine.class);
	
	@Before
	public void before() throws ClientProtocolException, IOException {
		when(httpResponse.getStatusLine()).thenReturn(statusLine);
		when(statusLine.getStatusCode()).thenReturn(200).thenReturn(500); 
		when(httpClient.execute(anyObject())).thenReturn(httpResponse);		
	}
	@Test
	public void testStatusResponse() throws ClientProtocolException, IOException {
		Restee4jClient.Restee4jClientBuilder.create(this.httpClient).build();
		Assertions.assertThat(httpClient.execute(new HttpGet()).getStatusLine().getStatusCode()).isEqualTo(200);
		Assertions.assertThat(httpClient.execute(new HttpGet()).getStatusLine().getStatusCode()).isEqualTo(500);
	}
}
