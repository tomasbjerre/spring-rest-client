package se.bjurr.springresttemplateclient.parse.model;

import java.util.Optional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

public class RequestDetails {

  private final HttpMethod requestMethod;
  private final String requestPath;
  private final MediaType consumes;
  private final MediaType produces;
  private final HttpHeaders httpHeaders;

  public RequestDetails(
      final HttpMethod requestMethod,
      final String requestPath,
      final MediaType consumes,
      final MediaType produces,
      final HttpHeaders httpHeaders) {
    this.requestMethod = requestMethod;
    this.requestPath = requestPath;
    this.consumes = consumes;
    this.produces = produces;
    this.httpHeaders = httpHeaders;
  }

  public Optional<MediaType> findConsumes() {
    return Optional.ofNullable(this.consumes);
  }

  public Optional<MediaType> findProduces() {
    return Optional.ofNullable(this.produces);
  }

  public HttpMethod getRequestMethod() {
    return this.requestMethod;
  }

  public String getRequestPath() {
    return this.requestPath;
  }

  public HttpHeaders getHttpHeaders() {
    return this.httpHeaders;
  }

  @Override
  public String toString() {
    return "RequestDetails [requestMethod="
        + this.requestMethod
        + ", requestPath="
        + this.requestPath
        + ", consumes="
        + this.consumes
        + ", produces="
        + this.produces
        + ", httpHeaders="
        + this.httpHeaders
        + "]";
  }
}
