package se.bjurr.springresttemplateclient.test.testcases;

import org.junit.Test;
import se.bjurr.springresttemplateclient.test.spec.api.UserApi;
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
}
