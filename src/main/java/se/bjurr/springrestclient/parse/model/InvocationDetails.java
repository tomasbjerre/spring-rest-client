package se.bjurr.springrestclient.parse.model;

import java.util.Map;
import java.util.Optional;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;

public class InvocationDetails {

  private final RequestDetails requestDetails;
  private final MultiValueMap<String, String> queryParams;
  private final Map<String, String> pathVariables;
  private final Object requestBody;
  private final boolean methodReturnTypeIsResponseEntity;
  private final ParameterizedTypeReference<?> responseType;
  private final HttpHeaders headers;

  public InvocationDetails(
      final RequestDetails requestDetails,
      final MultiValueMap<String, String> queryParams,
      final Map<String, String> pathVariables,
      final Object requestBody,
      final boolean methodReturnTypeIsResponseEntity,
      final ParameterizedTypeReference<?> responseType,
      final HttpHeaders headers) {
    this.requestDetails = requestDetails;
    this.queryParams = queryParams;
    this.pathVariables = pathVariables;
    this.requestBody = requestBody;
    this.methodReturnTypeIsResponseEntity = methodReturnTypeIsResponseEntity;
    this.responseType = responseType;
    this.headers = headers;
  }

  public Map<String, String> getPathVariables() {
    return this.pathVariables;
  }

  public MultiValueMap<String, String> getQueryParams() {
    return this.queryParams;
  }

  public HttpHeaders getHeaders() {
    return this.headers;
  }

  public Optional<Object> findRequestBody() {
    return Optional.ofNullable(this.requestBody);
  }

  public RequestDetails getRequestDetails() {
    return this.requestDetails;
  }

  public ParameterizedTypeReference<?> getResponseType() {
    return this.responseType;
  }

  public boolean isMethodReturnTypeResponseEntity() {
    return this.methodReturnTypeIsResponseEntity;
  }

  @Override
  public String toString() {
    return "InvocationDetails [requestDetails="
        + this.requestDetails
        + ", queryParams="
        + this.queryParams
        + ", pathVariables="
        + this.pathVariables
        + ", requestBody="
        + this.requestBody
        + ", methodReturnTypeIsResponseEntity="
        + this.methodReturnTypeIsResponseEntity
        + ", responseType="
        + this.responseType
        + ", headers="
        + this.headers
        + "]";
  }
}
