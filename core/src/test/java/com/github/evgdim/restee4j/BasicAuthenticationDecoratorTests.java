package com.github.evgdim.restee4j;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

import org.apache.http.Header;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class BasicAuthenticationDecoratorTests {
	private static final String PASSWORD = "secret";
	private static final String USERNAME = "evgeni";
	private static final String BASIC = "Basic ";
	private static final String AUTHORIZATION = "Authorization";
	private CloseableHttpClient httpClient = mock(CloseableHttpClient.class);
	private CloseableHttpResponse httpResponse = mock(CloseableHttpResponse.class);
	private StatusLine statusLine = mock(StatusLine.class);
	
	@Before
	public void before() throws ClientProtocolException, IOException {
		when(httpResponse.getStatusLine()).thenReturn(statusLine);
		when(statusLine.getStatusCode()).thenReturn(200); 
		when(httpClient.execute(anyObject())).thenReturn(httpResponse);		
	}
	@Test
	public void testStatusResponse() throws ClientProtocolException, IOException {
		Restee4jClient restee4jClient = Restee4jClient.Restee4jClientBuilder.create(this.httpClient).basicAuthentication(USERNAME, PASSWORD).build();
		Assertions.assertThat(restee4jClient.execute(new HttpGet()).getStatusLine().getStatusCode()).isEqualTo(200);
		
		ArgumentCaptor<HttpGet> captor = ArgumentCaptor.forClass(HttpGet.class);
		Mockito.verify(httpClient).execute(captor.capture());
		Optional<Header> authHeader = Arrays.stream(captor.getValue().getAllHeaders()).filter(h -> h.getName().equals(AUTHORIZATION)).findFirst();
		String encodeToString = Base64.getEncoder().encodeToString((USERNAME + ":" + PASSWORD).getBytes());
		
		Assertions.assertThat(authHeader.isPresent()).isEqualTo(true);
		Assertions.assertThat(authHeader.get().getName()).isEqualTo(AUTHORIZATION);
		Assertions.assertThat(authHeader.get().getValue()).isEqualTo(BASIC + encodeToString);
	}
}
