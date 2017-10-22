package com.github.evgdim.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class JsonResteeClient implements ResteeClient{
    private Client client;
    private ObjectMapper jacksonMapper;
    private String baseUrl;

    public JsonResteeClient(Client client, ObjectMapper jacksonMapper, String baseUrl) {
        this.client = client;
        this.jacksonMapper = jacksonMapper;
        this.baseUrl = baseUrl;
    }

    public <R> R get(String url, MultivaluedMap<String, String> parameters, Class<R> result) throws IOException {
        ClientResponse response = get(url, parameters);
        String responseEntity = response.getEntity(String.class);
        if(String.class.equals(result)) {
            return (R)responseEntity;
        }
        R r = jacksonMapper.readValue(responseEntity, result);
        return r;
    }

    public <R> R get(String url, MultivaluedMap<String, String> parameters, Map<String, String> headers, Class<R> result) {
        return null;
    }

    @Override
    public ClientResponse get(String url, MultivaluedMap<String, String> parameters) {
        WebResource webResource = client.resource(baseUrl+url);
        ClientResponse response = webResource
                .queryParams(parameters)
                .accept("application/json")
                .get(ClientResponse.class);
        return response;
    }


}
