package se.bjurr.springrestclient.parse;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/** Extracts path, query and header values from an invoked method's annotated parameters. */
final class MethodArgumentExtractor {

  private MethodArgumentExtractor() {}

  static Map<String, String> getPathVariables(final Method method, final Object... args) {
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

  static MultiValueMap<String, String> getRequestVariables(
      final Method method, final Object... args) {
    final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    for (int i = 0; i < method.getParameterCount(); i++) {
      final Parameter p = method.getParameters()[i];
      final RequestParam rp = p.getAnnotation(RequestParam.class);
      if (rp != null) {
        final Object arg = args[i];
        if (arg != null) {
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
    }
    return map;
  }

  static void addHeaderVariables(
      final Method method, final HttpHeaders headers, final Object... args) {
    final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    for (int i = 0; i < method.getParameterCount(); i++) {
      final Parameter p = method.getParameters()[i];
      final RequestHeader rh = p.getAnnotation(RequestHeader.class);
      if (rh != null) {
        map.add(rh.value(), args[i].toString());
      }
    }
    for (final Entry<String, List<String>> header : map.entrySet()) {
      for (final String value : header.getValue()) {
        headers.add(header.getKey(), value);
      }
    }
  }
}
