/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.27).
 * https://github.com/swagger-api/swagger-codegen Do not edit the class manually.
 */
package se.bjurr.springrestclient.test.requestmapping.spec.api;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import se.bjurr.springrestclient.test.requestmapping.spec.model.User;

public interface UserApi {

  @RequestMapping(
      value = "/user",
      consumes = {"application/json"},
      method = RequestMethod.POST)
  ResponseEntity<Void> createUser(@RequestBody User body);

  @RequestMapping(
      value = "/user/createWithArray",
      consumes = {"application/json"},
      method = RequestMethod.POST)
  ResponseEntity<Void> createUsersWithArrayInput(@RequestBody List<User> body);

  @RequestMapping(
      value = "/user/createWithList",
      consumes = {"application/json"},
      method = RequestMethod.POST)
  ResponseEntity<Void> createUsersWithListInput(@RequestBody List<User> body);

  @RequestMapping(value = "/user/{username}", method = RequestMethod.DELETE)
  ResponseEntity<Void> deleteUser(@PathVariable("username") String username);

  @RequestMapping(
      value = "/user/{username}",
      produces = {"application/json"},
      method = RequestMethod.GET)
  ResponseEntity<User> getUserByName(@PathVariable("username") String username);

  @RequestMapping(
      value = "/user/login",
      produces = {"application/json"},
      method = RequestMethod.GET)
  ResponseEntity<String> loginUser(
      @RequestParam(value = "username", required = true) String username,
      @RequestParam(value = "password", required = true) String password);

  @RequestMapping(value = "/user/logout", method = RequestMethod.GET)
  ResponseEntity<Void> logoutUser();

  @RequestMapping(
      value = "/user/{username}",
      consumes = {"application/json"},
      method = RequestMethod.PUT)
  ResponseEntity<Void> updateUser(@RequestBody User body);
}
