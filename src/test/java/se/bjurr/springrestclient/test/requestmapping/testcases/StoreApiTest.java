package se.bjurr.springrestclient.test.requestmapping.testcases;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import se.bjurr.springrestclient.test.requestmapping.spec.api.StoreApi;
import se.bjurr.springrestclient.test.requestmapping.spec.model.Order;
import se.bjurr.springrestclient.test.utils.BaseApiTest;

public class StoreApiTest extends BaseApiTest<StoreApi> {

  @Override
  public Class<StoreApi> getSutClass() {
    return StoreApi.class;
  }

  @Test
  public void deleteOrder() {
    this.getSut().deleteOrder(123L);
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
          "url" : "http://localhost:X/store/order/123"
        } ]""");
  }

  @Test
  public void getOrderById() {
    this.mockResponse(MediaType.APPLICATION_JSON_VALUE, "{\"id\":456}");

    final Order actual = this.getSut().getOrderById(456L);

    assertThat(actual.getId()).isEqualTo(456L);
    this.verify(
        """
        [ {
          "body" : "",
          "cookies" : { },
          "headers" : {
            "Accept" : "application/json",
            "Connection" : "keep-alive"
          },
          "method" : "GET",
          "url" : "http://localhost:X/store/order/456"
        } ]""");
  }

  @Test
  public void placeOrder() {
    this.mockResponse(
        MediaType.APPLICATION_JSON_VALUE,
        "{\"id\":456,\"petId\":null,\"quantity\":null,\"shipDate\":null,\"status\":null,\"complete\":false}");

    final Order body = new Order().id(123L);
    final Order actual = this.getSut().placeOrder(body);

    assertThat(actual.getId()).isEqualTo(456L);
    this.verify(
        """
        [ {
          "body" : "{\\"complete\\":false,\\"id\\":123,\\"petId\\":null,\\"quantity\\":null,\\"shipDate\\":null,\\"status\\":null}",
          "cookies" : { },
          "headers" : {
            "Accept" : "application/json",
            "Connection" : "keep-alive",
            "Content-Type" : "application/json",
            "Transfer-Encoding" : "chunked"
          },
          "method" : "POST",
          "url" : "http://localhost:X/store/order"
        } ]""");
  }

  @Test
  public void getInventory() {
    this.mockResponse(MediaType.APPLICATION_JSON_VALUE, "{\"k\":1}");

    final Map<String, Integer> actual = this.getSut().getInventory();

    assertThat(actual).containsEntry("k", 1);
    this.verify(
        """
        [ {
          "body" : "",
          "cookies" : { },
          "headers" : {
            "Accept" : "application/json",
            "Connection" : "keep-alive"
          },
          "method" : "GET",
          "url" : "http://localhost:X/store/inventory"
        } ]""");
  }
}
