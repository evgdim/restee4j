package com.github.evgdim.restee4j;

import java.util.Map;

public class Response<T> {
    private int status;
    private T payload;
    private Map<String, String> headers;

    public Response(int status, T payload, Map<String, String> headers) {
        this.status = status;
        this.payload = payload;
        this.headers = headers;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public String toString() {
        return "Response{" +
                "status=" + status +
                ", payload=" + payload +
                ", headers=" + headers +
                '}';
    }
}
