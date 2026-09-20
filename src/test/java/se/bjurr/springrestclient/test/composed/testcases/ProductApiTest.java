package se.bjurr.springrestclient.test.composed.testcases;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import se.bjurr.springrestclient.test.composed.spec.api.ProductApi;
import se.bjurr.springrestclient.test.composed.spec.model.Product;
import se.bjurr.springrestclient.test.composed.spec.model.ProductInput;
import se.bjurr.springrestclient.test.utils.BaseApiTest;

public class ProductApiTest extends BaseApiTest<ProductApi> {

  @Override
  public Class<ProductApi> getSutClass() {
    return ProductApi.class;
  }

  @Test
  public void addProduct() {
    this.mockResponse(MediaType.APPLICATION_JSON_VALUE, "{\"name\":\"a\"}");

    final Product actual = this.getSut().addProduct(new ProductInput(1));

    assertThat(actual.getName()).isEqualTo("a");

    this.verify(
        """
        [ {
          "body" : "{\\"id\\":1}",
          "cookies" : { },
          "headers" : {
            "Accept" : "application/json, application/*+json",
            "Connection" : "keep-alive",
            "Content-Type" : "application/json",
            "Transfer-Encoding" : "chunked"
          },
          "method" : "POST",
          "url" : "http://localhost:X/product"
        } ]""");
  }

  @Test
  public void getProduct() {
    this.mockResponse(MediaType.APPLICATION_JSON_VALUE, "{\"name\":\"a\"}");

    final Product actual = this.getSut().getProduct("1");

    assertThat(actual.getName()).isEqualTo("a");

    this.verify(
        """
        [ {
          "body" : "",
          "cookies" : { },
          "headers" : {
            "Accept" : "application/json, application/*+json",
            "Connection" : "keep-alive"
          },
          "method" : "GET",
          "url" : "http://localhost:X/product/1"
        } ]""");
  }

  @Test
  public void updateProduct() {
    this.mockResponse(
        MediaType.APPLICATION_JSON_VALUE, "{\"id\": 1, \"name\":\"a\", \"price\": 1.2}");

    final Product actual = this.getSut().updateProduct("1", new ProductInput(2));

    assertThat(actual.getName()).isEqualTo("a");

    this.verify(
        """
        [ {
          "body" : "{\\"id\\":2}",
          "cookies" : { },
          "headers" : {
            "Accept" : "application/json, application/*+json",
            "Connection" : "keep-alive",
            "Content-Type" : "application/json",
            "Transfer-Encoding" : "chunked"
          },
          "method" : "PUT",
          "url" : "http://localhost:X/product/1"
        } ]""");
  }

  @Test
  public void deleteProduct() {
    this.getSut().deleteProduct("1");

    this.verify(
        """
        [ {
          "body" : "",
          "cookies" : { },
          "headers" : {
            "Accept" : "application/json, application/*+json",
            "Connection" : "keep-alive",
            "Content-Length" : "0",
            "Content-Type" : "application/x-www-form-urlencoded"
          },
          "method" : "DELETE",
          "url" : "http://localhost:X/product/1"
        } ]""");
  }
}
