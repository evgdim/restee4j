package com.github.evgdim;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.evgdim.client.JsonResteeClient;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppTest {
    private Client jerseyclient = Client.create();
    private ObjectMapper mapper = new ObjectMapper();
    private JsonResteeClient client = new JsonResteeClient(jerseyclient, mapper, "http://localhost:4567");
    @Test
    public void testJsonString() throws IOException {
        String result = client.get("/people", new MultivaluedMapImpl(), String.class);
        assertThat(result, not(isEmptyOrNullString()));
    }

    @Test
    public void testJsonObject() throws IOException {
        ClientResponse clientResponse = client.get("/people", new MultivaluedMapImpl());
        assertThat("status", clientResponse.getStatus() == 200);
        assertThat(clientResponse.getEntity(String.class), not(isEmptyOrNullString()));
    }

    @Test
    public void testNotFound() throws IOException {
        ClientResponse clientResponse = client.get("/people/999", new MultivaluedMapImpl());
        assertThat("status", clientResponse.getStatus() == 404);
        assertThat(clientResponse.getEntity(String.class), not(isEmptyOrNullString()));
    }
}
