package se.bjurr.springrestclient.parse;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Optional;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import se.bjurr.springrestclient.parse.model.InvocationDetails;
import se.bjurr.springrestclient.parse.model.RequestDetails;

public final class InvocationParser {

  private InvocationParser() {}

  public static <T> Optional<T> findAnnotation(
      final Class<?> clazz, final Class<T> findAnnotation) {
    final Annotation[] methodAnnotations = clazz.getAnnotations();
    return findAnnotation(methodAnnotations, findAnnotation);
  }

  public static <T> Optional<T> findAnnotation(final Method method, final Class<T> findAnnotation) {
    final Annotation[] methodAnnotations = method.getAnnotations();
    return findAnnotation(methodAnnotations, findAnnotation);
  }

  @SuppressWarnings("unchecked")
  private static <T> Optional<T> findAnnotation(
      final Annotation[] methodAnnotations, final Class<T> annotations) {
    for (final Annotation annotation : methodAnnotations) {
      if (annotation.annotationType() == annotations) {
        return Optional.of((T) annotation);
      }
    }
    return Optional.empty();
  }

  public static <T> T getAnnotation(
      final Method method, final Class<T> clazz, final String message) {
    final Optional<T> requestMapping = InvocationParser.findAnnotation(method, clazz);
    if (!requestMapping.isPresent()) {
      throw new RuntimeException(message);
    }
    return requestMapping.get();
  }

  public static Optional<Object> findRequestBody(final Method method, final Object... args) {
    for (int i = 0; i < method.getParameterCount(); i++) {
      final Parameter p = method.getParameters()[i];
      final RequestBody rb = p.getAnnotation(RequestBody.class);
      if (rb != null) {
        return Optional.of(args[i]);
      }
    }
    return Optional.empty();
  }

  public static Type getGenericTypeOfMethod(final Object proxy, final Method method) {
    final ResolvableType r = ResolvableType.forMethodReturnType(method);
    return r.getGeneric(0).getType();
  }

  public static InvocationDetails getInvocationDetails(
      final Object proxy, final Method method, final Object... args) throws ClassNotFoundException {
    final RequestDetails requestDetails = getRequestDetails(method);

    final MultiValueMap<String, String> queryParams =
        MethodArgumentExtractor.getRequestVariables(method, args);

    final Map<String, String> pathVariables =
        MethodArgumentExtractor.getPathVariables(method, args);

    final Optional<Object> requestBody = InvocationParser.findRequestBody(method, args);

    final boolean methodReurnTypeIsResponseEntity =
        method.getReturnType().isAssignableFrom(ResponseEntity.class);

    ParameterizedTypeReference<?> responseType;
    if (methodReurnTypeIsResponseEntity) {
      responseType =
          new ParameterizedTypeReference<Type>() {
            @Override
            public Type getType() {
              return InvocationParser.getGenericTypeOfMethod(proxy, method);
            }
          };
    } else {
      responseType =
          new ParameterizedTypeReference<Type>() {
            @Override
            public Type getType() {
              return method.getGenericReturnType();
            }
          };
    }

    final HttpHeaders headers = requestDetails.getHttpHeaders();
    MethodArgumentExtractor.addHeaderVariables(method, headers, args);

    return new InvocationDetails(
        requestDetails,
        queryParams,
        pathVariables,
        requestBody.orElse(null),
        methodReurnTypeIsResponseEntity,
        responseType,
        headers);
  }

  private static RequestDetails getRequestDetails(final Method method) {
    final Optional<RequestMapping> classLevelRequestMappingOpt =
        InvocationParser.findAnnotation(method.getDeclaringClass(), RequestMapping.class);

    final Optional<RequestMapping> requestMapping =
        InvocationParser.findAnnotation(method, RequestMapping.class);
    if (requestMapping.isPresent()) {
      return RequestMappingParser.getRequestDetails(
          requestMapping.get(), classLevelRequestMappingOpt.orElse(null));
    }
    final Optional<RequestMapping> composedOpt = ComposedRequestMappingResolver.resolve(method);
    if (composedOpt.isPresent()) {
      return RequestMappingParser.getRequestDetails(
          composedOpt.get(), classLevelRequestMappingOpt.orElse(null));
    }

    throw new RuntimeException(
        "Did not find any supported annotation on "
            + method.getDeclaringClass()
            + " "
            + method.getName()
            + ". Pull requests, or issues, are welcome on GitHub!");
  }
}
