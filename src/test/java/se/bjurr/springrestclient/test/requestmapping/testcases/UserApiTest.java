package se.bjurr.springrestclient.test.requestmapping.testcases;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import se.bjurr.springrestclient.test.requestmapping.spec.api.UserApi;
import se.bjurr.springrestclient.test.requestmapping.spec.model.User;
import se.bjurr.springrestclient.test.utils.BaseApiTest;

public class UserApiTest extends BaseApiTest<UserApi> {

  @Override
  public Class<UserApi> getSutClass() {
    return UserApi.class;
  }

  @Test
  public void getUserByName() {
    this.mockResponse(
        MediaType.APPLICATION_JSON_VALUE,
        "{\"id\":null,\"username\":null,\"firstName\":null,\"lastName\":null,\"email\":\"asdasd\",\"password\":null,\"phone\":null,\"userStatus\":null}");

    final ResponseEntity<User> actual = this.getSut().getUserByName("tomas");

    assertThat(actual.getBody().getEmail()).isEqualTo("asdasd");
    this.verify();
  }

  @Test
  public void createUser() {
    final User body = new User().email("asdasd");
    this.getSut().createUser(body);
    this.verify();
  }

  @Test
  public void createUsersWithArrayInput() {
    final List<User> body = Arrays.asList(new User().email("asdasd"));
    this.getSut().createUsersWithArrayInput(body);
    this.verify();
  }

  @Test
  public void loginUser() {
    this.mockResponse(MediaType.TEXT_PLAIN_VALUE, "whatever");
    this.getSut().loginUser("a", "b");
    this.verify();
  }
}
