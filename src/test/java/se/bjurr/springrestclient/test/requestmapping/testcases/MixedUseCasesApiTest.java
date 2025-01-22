package se.bjurr.springrestclient.test.requestmapping.testcases;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Test;
import se.bjurr.springrestclient.test.requestmapping.spec.api.MixedUseCasesApi;
import se.bjurr.springrestclient.test.utils.BaseApiTest;

public class MixedUseCasesApiTest extends BaseApiTest<MixedUseCasesApi> {

  @Override
  public Class<MixedUseCasesApi> getSutClass() {
    return MixedUseCasesApi.class;
  }

  /**
   * If compiled with '-parameters' this will not fail. If not, (pathVariables={arg0=1}), it will
   * fail.
   */
  // @Test
  public void doGetNoNameInPathVariable() {
    assertThatThrownBy(() -> this.getSut().doGetNoNameInPathVariable(1L))
        .isExactlyInstanceOf(RuntimeException.class)
        .hasMessage(
            "InvocationDetails [requestDetails=RequestDetails [requestMethod=GET, requestPath=/base/doget/{theId}, consumes=null, produces=null, httpHeaders={}], queryParams={}, pathVariables={arg0=1}, requestBody=null, methodReturnTypeIsResponseEntity=true, responseType=ParameterizedTypeReference<interface java.lang.reflect.Type>, headers={}]");
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
