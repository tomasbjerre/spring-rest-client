package se.bjurr.springrestclient.parse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Resolves Spring's composed mapping annotations, e.g. {@code @GetMapping}, to a RequestMapping.
 */
final class ComposedRequestMappingResolver {

  private static final Map<Class<?>, RequestMethod> MAPPINGS = new HashMap<>();

  static {
    MAPPINGS.put(DeleteMapping.class, RequestMethod.DELETE);
    MAPPINGS.put(GetMapping.class, RequestMethod.GET);
    MAPPINGS.put(PatchMapping.class, RequestMethod.PATCH);
    MAPPINGS.put(PostMapping.class, RequestMethod.POST);
    MAPPINGS.put(PutMapping.class, RequestMethod.PUT);
  }

  private ComposedRequestMappingResolver() {}

  static Optional<RequestMapping> resolve(final Method method) {
    for (final Entry<Class<?>, RequestMethod> mapping : MAPPINGS.entrySet()) {
      final Class<?> composedAnnotationType = mapping.getKey();
      final RequestMethod requestMethod = mapping.getValue();
      final Optional<?> opt = InvocationParser.findAnnotation(method, composedAnnotationType);
      if (opt.isPresent()) {
        return Optional.of(createRequestMappingProxy(requestMethod, opt.get()));
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
        Proxy.newProxyInstance(
            Thread.currentThread().getContextClassLoader(), clazz, invocationHandler);
  }
}
