package com.github.evgdim.restee4j.decorator.oauth;

import org.apache.http.impl.client.CloseableHttpClient;

public interface TokenRequester {
	public TokenData requestToken(CloseableHttpClient httpClient);
}
