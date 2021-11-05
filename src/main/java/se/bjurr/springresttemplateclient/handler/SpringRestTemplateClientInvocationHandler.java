package se.bjurr.springresttemplateclient.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.RequestEntity.BodyBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import se.bjurr.springresttemplateclient.parse.InvocationParser;

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

    final Optional<RequestMapping> requestMapping =
        InvocationParser.findAnnotation(method, RequestMapping.class);
    if (!requestMapping.isPresent()) {
      throw new RuntimeException(
          "Only RequestMapping is, currently, implemented. PR:s are welcome.");
    }

    if (requestMapping.get().method().length != 1) {
      throw new RuntimeException(
          "Only one request method, currently, supported. PR:s are welcome.");
    }
    final HttpMethod requestMethod = HttpMethod.valueOf(requestMapping.get().method()[0].name());

    if (requestMapping.get().value().length != 1) {
      throw new RuntimeException("Only one request path, currently, supported. PR:s are welcome.");
    }
    final String requestPath = requestMapping.get().value()[0];

    if (requestMapping.get().consumes().length > 1) {
      throw new RuntimeException(
          "Only one, or zero, consumes, currently, supported. PR:s are welcome.");
    }
    String consumes = null;
    if (requestMapping.get().consumes().length == 1) {
      consumes = requestMapping.get().consumes()[0];
    }

    if (requestMapping.get().produces().length > 1) {
      throw new RuntimeException(
          "Only one, or zero, produces, currently, supported. PR:s are welcome.");
    }
    String produces = null;
    if (requestMapping.get().produces().length == 1) {
      produces = requestMapping.get().produces()[0];
    }

    final Map<String, String> uriVariables = InvocationParser.getPathVariables(method, args);
    final URI uri =
        UriComponentsBuilder.fromHttpUrl(this.url) //
            .path(requestPath)
            .build(uriVariables);

    final Optional<Object> requestBody = InvocationParser.findReqestBody(method, args);

    final BodyBuilder b = RequestEntity.method(requestMethod, uri);

    if (consumes != null) {
      b.contentType(MediaType.parseMediaType(consumes));
    }

    if (produces != null) {
      b.accept(MediaType.parseMediaType(produces));
    }

    RequestEntity<?> requestEntity;
    if (requestBody.isPresent()) {
      requestEntity = b.body(requestBody.get());
    } else {
      requestEntity = b.build();
    }

    final boolean methodReurnTypeIsResponseEntity =
        method.getReturnType().isAssignableFrom(ResponseEntity.class);
    if (methodReurnTypeIsResponseEntity) {
      final Class<?> responseType = this.getGenericTypeOfResponseEntity(proxy, method);
      return this.restTemplate.exchange(requestEntity, responseType);
    } else {
      final Class<?> responseType = method.getReturnType();
      return this.restTemplate.exchange(requestEntity, responseType).getBody();
    }
  }

  private Class<?> getGenericTypeOfResponseEntity(final Object proxy, final Method method)
      throws ClassNotFoundException {
    Class<?> responseType;
    final String typeName = method.getGenericReturnType().getTypeName();
    final Pattern pattern = Pattern.compile("<(.*?)>");
    final Matcher matcher = pattern.matcher(typeName);
    if (!matcher.find()) {
      throw new RuntimeException("Cannot find generic type of " + typeName);
    }
    responseType = proxy.getClass().getClassLoader().loadClass(matcher.group(1));
    return responseType;
  }
}
