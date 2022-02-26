package se.bjurr.springresttemplateclient.test.composed.spec.testcases;

import se.bjurr.springresttemplateclient.test.composed.spec.api.UserApi;
import se.bjurr.springresttemplateclient.test.utils.BaseApiTest;

public class UserApiTest extends BaseApiTest<UserApi> {

  @Override
  public Class<UserApi> getSutClass() {
    return UserApi.class;
  }
}
