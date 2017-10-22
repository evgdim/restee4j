package com.github.evgdim.client;

import com.sun.jersey.api.client.ClientResponse;

import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.util.Map;

public interface ResteeClient {
    <R> R get(String url, MultivaluedMap<String, String> parameters, Class<R> result) throws IOException;
    <R> R get(String url, MultivaluedMap<String, String> parameters, Map<String, String> headers, Class<R> result);
    ClientResponse get(String url, MultivaluedMap<String, String> parameters);
}
