package se.bjurr.springresttemplateclient.parse;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public final class InvocationParser {

  private InvocationParser() {}

  public static RequestMethod getRequestMethod(final Method method) {
    if (findAnnotation(method, GetMapping.class).isPresent()) {
      return RequestMethod.GET;
    } else if (findAnnotation(method, PostMapping.class).isPresent()) {
      return RequestMethod.POST;
    } else if (findAnnotation(method, PutMapping.class).isPresent()) {
      return RequestMethod.PUT;
    } else if (findAnnotation(method, DeleteMapping.class).isPresent()) {
      return RequestMethod.DELETE;
    } else if (findAnnotation(method, PatchMapping.class).isPresent()) {
      return RequestMethod.PATCH;
    } else {
      final Optional<RequestMapping> requestMapping = findAnnotation(method, RequestMapping.class);
      if (requestMapping.isPresent()) {
        return requestMapping.get().method()[0];
      }
    }
    throw new RuntimeException("Cannot find request method of " + method.getName());
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

  public static Map<String, String> getPathVariables(final Method method, final Object[] args) {
    final Map<String, String> map = new HashMap<>();

    for (int i = 0; i < method.getParameterCount(); i++) {
      final Parameter p = method.getParameters()[i];

      final PathVariable pv = p.getAnnotation(PathVariable.class);
      if (pv != null) {
        map.put(pv.value(), args[i].toString());
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
        map.add(rp.value(), args[i].toString());
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

  public static Class<?> getGenericTypeOfMethod(final Object proxy, final Method method)
      throws ClassNotFoundException {
    final String typeName = method.getGenericReturnType().getTypeName();
    final Pattern pattern = Pattern.compile("<(.*?)>");
    final Matcher matcher = pattern.matcher(typeName);
    if (!matcher.find()) {
      throw new RuntimeException("Cannot find generic type of " + typeName);
    }
    return proxy.getClass().getClassLoader().loadClass(matcher.group(1));
  }
}
