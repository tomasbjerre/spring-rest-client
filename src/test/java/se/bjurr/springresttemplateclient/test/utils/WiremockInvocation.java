package se.bjurr.springresttemplateclient.test.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.http.Cookie;
import com.github.tomakehurst.wiremock.http.RequestMethod;
import com.github.tomakehurst.wiremock.stubbing.ServeEvent;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class WiremockInvocation {

  private String url;
  private RequestMethod method;
  private final Map<String, String> headers = new TreeMap<>();
  private final Map<String, String> cookies = new TreeMap<>();
  private String body;

  public WiremockInvocation() {}

  public static String from(final List<ServeEvent> serveEvent) {
    final List<WiremockInvocation> wiList = new ArrayList<>();
    for (final ServeEvent se : serveEvent) {
      final WiremockInvocation wi = new WiremockInvocation();
      final LoggedRequest request = se.getRequest();
      wi.setUrl(
          request.getAbsoluteUrl().replaceAll("http://localhost:[0-9]+", "http://localhost:X"));
      for (final String header : request.getAllHeaderKeys()) {
        if (!Arrays.asList("Host", "User-Agent").contains(header)) {
          wi.addHeader(header, request.getHeader(header));
        }
      }
      wi.setBody(request.getBodyAsString());
      for (final Entry<String, Cookie> coolie : request.getCookies().entrySet()) {
        wi.setCookie(
            coolie.getKey(),
            coolie.getValue().getValues().stream().collect(Collectors.joining("|")));
      }
      wi.setMethod(request.getMethod());
      wiList.add(wi);
    }
    try {
      return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(wiList);
    } catch (final JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  private void setCookie(final String cookie, final String value) {
    this.cookies.put(cookie, value);
  }

  public Map<String, String> getCookies() {
    return this.cookies;
  }

  private void setMethod(final RequestMethod method) {
    this.method = method;
  }

  public RequestMethod getMethod() {
    return this.method;
  }

  private void setBody(final String body) {
    this.body = body;
  }

  public String getBody() {
    return this.body;
  }

  private void addHeader(final String header, final String value) {
    this.headers.put(header, value);
  }

  public Map<String, String> getHeaders() {
    return this.headers;
  }

  public void setUrl(final String url) {
    this.url = url;
  }

  public String getUrl() {
    return this.url;
  }
}
