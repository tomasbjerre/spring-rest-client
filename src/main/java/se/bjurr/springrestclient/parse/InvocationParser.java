package se.bjurr.springrestclient.parse;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import se.bjurr.springrestclient.parse.model.InvocationDetails;
import se.bjurr.springrestclient.parse.model.RequestDetails;

public final class InvocationParser {
  private static final Map<Class<?>, RequestMethod> MAPPINGS = new HashMap<>();

  static {
    MAPPINGS.put(DeleteMapping.class, RequestMethod.DELETE);
    MAPPINGS.put(GetMapping.class, RequestMethod.GET);
    MAPPINGS.put(PatchMapping.class, RequestMethod.PATCH);
    MAPPINGS.put(PostMapping.class, RequestMethod.POST);
    MAPPINGS.put(PutMapping.class, RequestMethod.PUT);
  }

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

  public static Map<String, String> getPathVariables(final Method method, final Object[] args) {
    final Map<String, String> map = new HashMap<>();

    for (int i = 0; i < method.getParameterCount(); i++) {
      final Parameter p = method.getParameters()[i];

      final PathVariable pv = p.getAnnotation(PathVariable.class);
      if (pv != null) {
        String name = pv.value();
        if (name.isEmpty()) {
          name = p.getName();
        }
        map.put(name, args[i].toString());
      }
    }
    return map;
  }

  public static MultiValueMap<String, String> getRequestVariables(
      final Method method, final Object[] args) {
    final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    for (int i = 0; i < method.getParameterCount(); i++) {
      final Parameter p = method.getParameters()[i];

      final RequestParam rp = p.getAnnotation(RequestParam.class);
      if (rp != null) {
        final Object arg = args[i];
        if (arg instanceof List) {
          @SuppressWarnings("unchecked")
          final List<Object> arr = (List<Object>) arg;
          for (final Object element : arr) {
            map.add(rp.value(), element.toString());
          }
        } else if (arg.getClass().isArray()) {
          final Object[] arr = (Object[]) arg;
          for (final Object element : arr) {
            map.add(rp.value(), element.toString());
          }
        } else {
          map.add(rp.value(), args[i].toString());
        }
      }
    }
    return map;
  }

  private static MultiValueMap<String, String> getHeaderVariables(
      final Method method, final Object[] args) {
    final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    for (int i = 0; i < method.getParameterCount(); i++) {
      final Parameter p = method.getParameters()[i];
      final RequestHeader rh = p.getAnnotation(RequestHeader.class);
      if (rh != null) {
        map.add(rh.value(), args[i].toString());
      }
    }
    return map;
  }

  public static Optional<Object> findReqestBody(final Method method, final Object[] args) {
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
      final Object proxy, final Method method, final Object[] args) throws ClassNotFoundException {
    final RequestDetails requestDetails = getRequestDetails(method);

    final MultiValueMap<String, String> queryParams =
        InvocationParser.getRequestVariables(method, args);

    final Map<String, String> pathVariables = InvocationParser.getPathVariables(method, args);

    final Optional<Object> requestBody = InvocationParser.findReqestBody(method, args);

    final boolean methodReurnTypeIsResponseEntity =
        method.getReturnType().isAssignableFrom(ResponseEntity.class);

    ParameterizedTypeReference<?> responseType = null;
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
    final MultiValueMap<String, String> headerParams =
        InvocationParser.getHeaderVariables(method, args);
    for (final Entry<String, List<String>> header : headerParams.entrySet()) {
      for (final String value : header.getValue()) {
        headers.add(header.getKey(), value);
      }
    }

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
    final Optional<RequestMapping> composedOpt =
        InvocationParser.findComposedRequestMappingAnnotation(method);
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

  private static Optional<RequestMapping> findComposedRequestMappingAnnotation(
      final Method method) {
    for (final Entry<Class<?>, RequestMethod> map : MAPPINGS.entrySet()) {
      final Class<?> composedAnnotationType = map.getKey();
      final RequestMethod requestMethod = map.getValue();
      final Optional<?> opt = InvocationParser.findAnnotation(method, composedAnnotationType);
      if (opt.isPresent()) {
        final Object composedAnnotationInstance = opt.get();
        final RequestMapping proxy =
            createRequestMappingProxy(requestMethod, composedAnnotationInstance);
        return Optional.of(proxy);
      }
    }
    return Optional.empty();
  }

  private static RequestMapping createRequestMappingProxy(
      final RequestMethod requestMethod, final Object composedAnnotationInstance) {
    final InvocationHandler invocationHandler =
        new InvocationHandler() {
          @Override
          public Object invoke(final Object o, final Method m, final Object[] args)
              throws Throwable {
            if (m.getName().equals("method")) {
              return new RequestMethod[] {requestMethod};
            }
            final Method declaredMethod =
                composedAnnotationInstance.getClass().getDeclaredMethod(m.getName());
            return declaredMethod.invoke(composedAnnotationInstance, args);
          }
        };
    final Class<?>[] clazz = {RequestMapping.class};
    return (RequestMapping)
        Proxy.newProxyInstance(InvocationParser.class.getClassLoader(), clazz, invocationHandler);
  }
}
