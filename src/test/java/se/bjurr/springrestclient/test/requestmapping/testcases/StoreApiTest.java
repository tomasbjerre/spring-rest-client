package se.bjurr.springrestclient.test.requestmapping.testcases;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.Test;
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
    this.verify();
  }

  @Test
  public void getOrderById() {
    this.mockResponse(MediaType.APPLICATION_JSON_VALUE, "{\"id\":456}");

    final Order actual = this.getSut().getOrderById(456L);

    assertThat(actual.getId()).isEqualTo(456L);
    this.verify();
  }

  @Test
  public void placeOrder() {
    this.mockResponse(
        MediaType.APPLICATION_JSON_VALUE,
        "{\"id\":456,\"petId\":null,\"quantity\":null,\"shipDate\":null,\"status\":null,\"complete\":false}");

    final Order body = new Order().id(123L);
    final Order actual = this.getSut().placeOrder(body);

    assertThat(actual.getId()).isEqualTo(456L);
    this.verify();
  }

  @Test
  public void getInventory() {
    this.mockResponse(MediaType.APPLICATION_JSON_VALUE, "{\"k\":1}");

    final Map<String, Integer> actual = this.getSut().getInventory();

    assertThat(actual).containsEntry("k", 1);
    this.verify();
  }
}
