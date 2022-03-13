package se.bjurr.springrestclient;

import java.lang.reflect.Proxy;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import se.bjurr.springrestclient.handler.SpringRestClientInvocationHandler;

public final class SpringRestClientBuilder {
  private SpringRestClientBuilder() {}

  public static class Builder<T> {
    private final Class<T> api;
    private RestTemplate restTemplate = new RestTemplate();
    private HttpHeaders unspecifiedHeaders = new HttpHeaders();
    private String url;

    public Builder(final Class<T> api) {
      if (api == null) {
        throw new IllegalArgumentException("api cannot be null");
      }
      this.api = api;
    }

    public Builder<T> setRestTemplate(final RestTemplate restTemplate) {
      this.restTemplate = restTemplate;
      return this;
    }

    public Builder<T> setUrl(final String url) {
      this.url = url;
      return this;
    }

    public Builder<T> setHeaders(final HttpHeaders headers) {
      this.unspecifiedHeaders = headers;
      return this;
    }

    public Builder<T> setHeader(final String headerName, final String headerValue) {
      this.unspecifiedHeaders.add(headerName, headerValue);
      return this;
    }

    @SuppressWarnings("unchecked")
    public T build() {
      if (this.url == null) {
        throw new IllegalArgumentException("url cannot be null");
      }
      final SpringRestClientInvocationHandler<T> invocationHandler =
          new SpringRestClientInvocationHandler<>(
              this.url, this.restTemplate, this.unspecifiedHeaders);

      return (T)
          Proxy.newProxyInstance(
              this.api.getClassLoader(), new Class[] {this.api}, invocationHandler);
    }
  }

  public static <T> Builder<T> springRestTemplateClientBuilder(final Class<T> clazz) {
    return new Builder<>(clazz);
  }

  public static <T> Builder<T> create(final Class<T> clazz) {
    return springRestTemplateClientBuilder(clazz);
  }
}
