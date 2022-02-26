package se.bjurr.springresttemplateclient.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.List;
import java.util.Map.Entry;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.RequestEntity.BodyBuilder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import se.bjurr.springresttemplateclient.parse.InvocationParser;
import se.bjurr.springresttemplateclient.parse.model.InvocationDetails;

public class SpringRestTemplateClientInvocationHandler<T> implements InvocationHandler {

  private final String url;
  private final RestTemplate restTemplate;
  private final HttpHeaders unspecifiedHeaders;

  public SpringRestTemplateClientInvocationHandler(
      final String url, final RestTemplate restTemplate, final HttpHeaders unspecifiedHeaders) {
    this.url = url;
    this.restTemplate = restTemplate;
    this.unspecifiedHeaders = unspecifiedHeaders;
  }

  @Override
  public Object invoke(final Object proxy, final Method method, final Object[] args)
      throws Throwable {
    final InvocationDetails invocationDetails =
        InvocationParser.getInvocationDetails(proxy, method, args);
    return this.doRequest(invocationDetails);
  }

  private Object doRequest(final InvocationDetails invocationDetails) {
    URI uri = null;
    try {
      uri =
          UriComponentsBuilder.fromHttpUrl(this.url) //
              .path(invocationDetails.getRequestDetails().getRequestPath())
              .queryParams(invocationDetails.getQueryParams())
              .buildAndExpand(invocationDetails.getPathVariables())
              .toUri();
    } catch (final IllegalArgumentException e) {
      throw new RuntimeException(invocationDetails.toString(), e);
    }
    final BodyBuilder bodyBuilder =
        RequestEntity.method(invocationDetails.getRequestDetails().getRequestMethod(), uri);

    for (final Entry<String, List<String>> header : invocationDetails.getHeaders().entrySet()) {
      final String[] stringArray = new String[header.getValue().size()];
      for (int i = 0; i < header.getValue().size(); i++) {
        stringArray[i] = header.getValue().get(i);
      }
      bodyBuilder.header(header.getKey(), stringArray);
    }

    if (invocationDetails.getRequestDetails().findConsumes().isPresent()) {
      bodyBuilder.contentType(invocationDetails.getRequestDetails().findConsumes().get());
    }

    if (invocationDetails.getRequestDetails().findProduces().isPresent()) {
      bodyBuilder.accept(invocationDetails.getRequestDetails().findProduces().get());
    }

    RequestEntity<?> requestEntity;
    if (invocationDetails.findRequestBody().isPresent()) {
      requestEntity =
          this.addUnspecifiedHeaders(bodyBuilder).body(invocationDetails.findRequestBody().get());
    } else {
      requestEntity = this.addUnspecifiedHeaders(bodyBuilder).build();
    }

    if (invocationDetails.isMethodReurnTypeResponseEntity()) {
      return this.restTemplate.exchange(requestEntity, invocationDetails.getResponseType());
    } else {
      return this.restTemplate
          .exchange(requestEntity, invocationDetails.getResponseType())
          .getBody();
    }
  }

  private BodyBuilder addUnspecifiedHeaders(final BodyBuilder bodyBuilder) {
    for (final Entry<String, List<String>> header : this.unspecifiedHeaders.entrySet()) {
      for (final String value : header.getValue()) {
        final String headerName = header.getKey();
        bodyBuilder.header(headerName, value);
      }
    }
    return bodyBuilder;
  }
}
