package se.bjurr.springresttemplateclient.test.composed.spec.testcases;

import se.bjurr.springresttemplateclient.test.composed.spec.api.ProductApi;
import se.bjurr.springresttemplateclient.test.utils.BaseApiTest;

public class ProductApiTest extends BaseApiTest<ProductApi> {

  @Override
  public Class<ProductApi> getSutClass() {
    return ProductApi.class;
  }
}
