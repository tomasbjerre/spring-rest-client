/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.27).
 * https://github.com/swagger-api/swagger-codegen Do not edit the class manually.
 */
package se.bjurr.springrestclient.test.requestmapping.spec.api;

import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import se.bjurr.springrestclient.test.requestmapping.spec.model.Order;

public interface StoreApi {

  @RequestMapping(value = "/store/order/{orderId}", method = RequestMethod.DELETE)
  Void deleteOrder(@PathVariable("orderId") Long orderId);

  @RequestMapping(
      value = "/store/inventory",
      produces = {"application/json"},
      method = RequestMethod.GET)
  Map<String, Integer> getInventory();

  @RequestMapping(
      value = "/store/order/{orderId}",
      produces = {"application/json"},
      method = RequestMethod.GET)
  Order getOrderById(@PathVariable("orderId") Long orderId);

  @RequestMapping(
      value = "/store/order",
      produces = {"application/json"},
      consumes = {"application/json"},
      method = RequestMethod.POST)
  Order placeOrder(@RequestBody Order body);
}
