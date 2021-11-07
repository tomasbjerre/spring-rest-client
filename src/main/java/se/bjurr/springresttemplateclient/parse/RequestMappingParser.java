package se.bjurr.springresttemplateclient.parse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import se.bjurr.springresttemplateclient.parse.model.RequestDetails;

public class RequestMappingParser {

  public static RequestDetails getRequestDetails(final RequestMapping requestMapping) {
    if (requestMapping.method().length != 1) {
      throw new RuntimeException(
          "Only one request method, currently, supported. PR:s are welcome.");
    }
    final HttpMethod requestMethod = HttpMethod.valueOf(requestMapping.method()[0].name());

    if (requestMapping.value().length != 1) {
      throw new RuntimeException("Only one request path, currently, supported. PR:s are welcome.");
    }
    final String requestPath = requestMapping.value()[0];

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

    return new RequestDetails(requestMethod, requestPath, consumes, produces, httpHeaders);
  }
}
