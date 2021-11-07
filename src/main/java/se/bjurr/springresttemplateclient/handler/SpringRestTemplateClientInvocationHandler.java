package se.bjurr.springresttemplateclient.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.URI;
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

  public SpringRestTemplateClientInvocationHandler(
      final String url, final RestTemplate restTemplate, final HttpHeaders headers) {
    this.url = url;
    this.restTemplate = restTemplate;
  }

  @Override
  public Object invoke(final Object proxy, final Method method, final Object[] args)
      throws Throwable {
    final InvocationDetails invocationDetails =
        InvocationParser.getInvocationDetails(proxy, method, args);
    return this.doRequest(invocationDetails);
  }

  private Object doRequest(final InvocationDetails invocationDetails) {
    final URI uri =
        UriComponentsBuilder.fromHttpUrl(this.url) //
            .path(invocationDetails.getRequestDetails().getRequestPath())
            .queryParams(invocationDetails.getQueryParams())
            .build(invocationDetails.getPathVariables());

    final BodyBuilder bodyBuilder =
        RequestEntity.method(invocationDetails.getRequestDetails().getRequestMethod(), uri);

    if (invocationDetails.getRequestDetails().findConsumes().isPresent()) {
      bodyBuilder.contentType(invocationDetails.getRequestDetails().findConsumes().get());
    }

    if (invocationDetails.getRequestDetails().findProduces().isPresent()) {
      bodyBuilder.accept(invocationDetails.getRequestDetails().findProduces().get());
    }

    RequestEntity<?> requestEntity;
    if (invocationDetails.findRequestBody().isPresent()) {
      requestEntity = bodyBuilder.body(invocationDetails.findRequestBody().get());
    } else {
      requestEntity = bodyBuilder.build();
    }

    if (invocationDetails.isMethodReurnTypeResponseEntity()) {
      return this.restTemplate.exchange(requestEntity, invocationDetails.getResponseType());
    } else {
      return this.restTemplate
          .exchange(requestEntity, invocationDetails.getResponseType())
          .getBody();
    }
  }
}
