package se.bjurr.springresttemplateclient.test.testcases;

import org.junit.Test;
import se.bjurr.springresttemplateclient.test.spec.api.StoreApi;
import se.bjurr.springresttemplateclient.test.spec.model.Order;
import se.bjurr.springresttemplateclient.test.utils.BaseApiTest;

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
    this.getSut().getOrderById(123L);
    this.verify();
  }

  @Test
  public void placeOrder() {
    final Order body = new Order().id(123L);
    this.getSut().placeOrder(body);
    this.verify();
  }

  @Test
  public void getInventory() {
    this.getSut().getInventory();
    this.verify();
  }
}
