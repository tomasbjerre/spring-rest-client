package se.bjurr.springresttemplateclient.test.testcases;

import java.util.Arrays;
import org.junit.Test;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import se.bjurr.springresttemplateclient.SpringRestTemplateClientBuilder;
import se.bjurr.springresttemplateclient.test.spec.api.StoreApi;
import se.bjurr.springresttemplateclient.test.utils.BaseApiTest;

public class SimpleExampleTest extends BaseApiTest<StoreApi> {

  @Override
  public Class<StoreApi> getSutClass() {
    return StoreApi.class;
  }

  @Test
  public void testExample() {
    final RestTemplate restTemplate = new RestTemplate();
    restTemplate.setMessageConverters(Arrays.asList(new MappingJackson2HttpMessageConverter()));

    final StoreApi storeApi =
        SpringRestTemplateClientBuilder.create(StoreApi.class)
            .setRestTemplate(restTemplate)
            .setUrl(this.getMockUrl())
            .build();

    storeApi.deleteOrder(1234L);

    this.verify();
  }
}
