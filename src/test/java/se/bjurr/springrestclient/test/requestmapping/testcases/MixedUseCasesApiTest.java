package se.bjurr.springrestclient.test.requestmapping.testcases;

import org.junit.Test;
import se.bjurr.springrestclient.test.requestmapping.spec.api.MixedUseCasesApi;
import se.bjurr.springrestclient.test.utils.BaseApiTest;

public class MixedUseCasesApiTest extends BaseApiTest<MixedUseCasesApi> {

  @Override
  public Class<MixedUseCasesApi> getSutClass() {
    return MixedUseCasesApi.class;
  }

  @Test(expected = RuntimeException.class)
  public void doGetNoNameInPathVariable() {
    this.getSut().doGetNoNameInPathVariable(1L);
    this.verify();
  }

  @Test
  public void doGetPath() {
    this.getSut().doGetPath(1L);
    this.verify();
  }

  @Test
  public void doGetNoPath() {
    this.getSut().doGetNoPath();
    this.verify();
  }

  @Test
  public void doGetSlashPath() {
    this.getSut().doGetSlashPath();
    this.verify();
  }
}
