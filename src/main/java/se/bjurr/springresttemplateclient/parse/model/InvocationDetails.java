package se.bjurr.springresttemplateclient.parse.model;

import java.util.Map;
import java.util.Optional;
import org.springframework.util.MultiValueMap;

public class InvocationDetails {

  private final RequestDetails requestDetails;
  private final MultiValueMap<String, String> queryParams;
  private final Map<String, String> pathVariables;
  private final Object requestBody;
  private final boolean methodReurnTypeIsResponseEntity;
  private final Class<?> responseType;

  public InvocationDetails(
      final RequestDetails requestDetails,
      final MultiValueMap<String, String> queryParams,
      final Map<String, String> pathVariables,
      final Object requestBody,
      final boolean methodReurnTypeIsResponseEntity,
      final Class<?> responseType) {
    this.requestDetails = requestDetails;
    this.queryParams = queryParams;
    this.pathVariables = pathVariables;
    this.requestBody = requestBody;
    this.methodReurnTypeIsResponseEntity = methodReurnTypeIsResponseEntity;
    this.responseType = responseType;
  }

  public Map<String, String> getPathVariables() {
    return this.pathVariables;
  }

  public MultiValueMap<String, String> getQueryParams() {
    return this.queryParams;
  }

  public Optional<Object> findRequestBody() {
    return Optional.ofNullable(this.requestBody);
  }

  public RequestDetails getRequestDetails() {
    return this.requestDetails;
  }

  public Class<?> getResponseType() {
    return this.responseType;
  }

  public boolean isMethodReurnTypeResponseEntity() {
    return this.methodReurnTypeIsResponseEntity;
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
        + ", methodReurnTypeIsResponseEntity="
        + this.methodReurnTypeIsResponseEntity
        + ", responseType="
        + this.responseType
        + "]";
  }
}
