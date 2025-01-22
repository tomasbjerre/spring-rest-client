package se.bjurr.springrestclient.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.List;
import java.util.Map.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.RequestEntity.BodyBuilder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import se.bjurr.springrestclient.parse.InvocationParser;
import se.bjurr.springrestclient.parse.model.InvocationDetails;

public class SpringRestClientInvocationHandler<T> implements InvocationHandler {
  private static final Logger LOGGER =
      LoggerFactory.getLogger(SpringRestClientInvocationHandler.class);

  private final String url;
  private final RestTemplate restTemplate;
  private final HttpHeaders unspecifiedHeaders;

  public SpringRestClientInvocationHandler(
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
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Invoking with: " + invocationDetails);
    }
    URI uri;
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

    if (invocationDetails.isMethodReturnTypeResponseEntity()) {
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
