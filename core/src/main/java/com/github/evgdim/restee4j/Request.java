package com.github.evgdim.restee4j;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class Request {
    private Method method;
    private Map<String, String> headers;
    private URL url;

    public Request(Method method, Map<String, String> headers, String urlString) throws MalformedURLException {
        this.method = method;
        this.headers = headers;
        this.url = new URL(urlString);

    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }
}
