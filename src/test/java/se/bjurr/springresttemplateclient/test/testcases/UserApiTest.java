package se.bjurr.springresttemplateclient.test.testcases;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.springframework.http.MediaType;
import se.bjurr.springresttemplateclient.test.spec.api.UserApi;
import se.bjurr.springresttemplateclient.test.spec.model.User;
import se.bjurr.springresttemplateclient.test.utils.BaseApiTest;

public class UserApiTest extends BaseApiTest<UserApi> {

  @Override
  public Class<UserApi> getSutClass() {
    return UserApi.class;
  }

  @Test
  public void getUserByName() {
    this.getSut().getUserByName("tomas");
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
