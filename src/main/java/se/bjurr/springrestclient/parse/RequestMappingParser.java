package se.bjurr.springrestclient.parse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import se.bjurr.springrestclient.parse.model.RequestDetails;

public class RequestMappingParser {

  public static RequestDetails getRequestDetails(
      final RequestMapping requestMapping, final RequestMapping classLevelRequestMappingOpt) {
    if (requestMapping.method().length != 1) {
      throw new RuntimeException(
          "Only one request method, currently, supported. PR:s are welcome.");
    }

    List<String> pathsOnMethod = getOneOrZeroPaths(requestMapping);
    if (classLevelRequestMappingOpt != null) {
      final List<String> pathsOnClassLevel = getOneOrZeroPaths(classLevelRequestMappingOpt);
      if (pathsOnMethod.isEmpty()) {
        pathsOnMethod.addAll(pathsOnClassLevel);
      } else {
        final List<String> newPaths = new ArrayList<>();
        for (final String path : pathsOnMethod) {
          final String concat = pathsOnClassLevel.get(0) + "/" + path;
          newPaths.add(concat.replaceAll("[/]+", "/"));
        }
        pathsOnMethod = newPaths;
      }
    }
    if (pathsOnMethod.isEmpty()) {
      throw new RuntimeException("No request path found: " + pathsOnMethod);
    }
    if (requestMapping.consumes().length > 1) {
      throw new RuntimeException(
          "Only one, or zero, consumes, currently, supported. PR:s are welcome.");
    }
    MediaType consumes = null;
    if (requestMapping.consumes().length == 1) {
      final String consumesString = requestMapping.consumes()[0];
      consumes = MediaType.parseMediaType(consumesString);
    }

    if (requestMapping.produces().length > 1) {
      throw new RuntimeException(
          "Only one, or zero, produces, currently, supported. PR:s are welcome.");
    }
    MediaType produces = null;
    if (requestMapping.produces().length == 1) {
      final String producesString = requestMapping.produces()[0];
      produces = MediaType.parseMediaType(producesString);
    }

    final HttpHeaders httpHeaders = new HttpHeaders();
    for (final String header : requestMapping.headers()) {
      final int equalityIndex = header.indexOf("=");
      if (equalityIndex == -1) {
        throw new RuntimeException("Cannot parse header '" + header + "'");
      }
      final String[] spitted = header.split("=");
      httpHeaders.add(spitted[0], spitted[1]);
    }

    final String requestPath = pathsOnMethod.get(0);
    final HttpMethod requestMethod = HttpMethod.valueOf(requestMapping.method()[0].name());
    return new RequestDetails(requestMethod, requestPath, consumes, produces, httpHeaders);
  }

  private static List<String> getOneOrZeroPaths(final RequestMapping requestMapping) {
    final List<String> paths = new ArrayList<>();
    paths.addAll(Arrays.asList(requestMapping.value()));
    paths.addAll(Arrays.asList(requestMapping.path()));
    if (paths.size() > 1) {
      throw new RuntimeException(
          "Only one request path, currently, supported. PR:s are welcome. Found: " + paths);
    }
    return paths;
  }
}
