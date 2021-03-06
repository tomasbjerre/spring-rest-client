package se.bjurr.springrestclient.test.requestmapping.testcases;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import se.bjurr.springrestclient.SpringRestClientBuilder;
import se.bjurr.springrestclient.test.requestmapping.spec.api.StoreApi;
import se.bjurr.springrestclient.test.utils.BaseApiTest;

public class SimpleExampleTest extends BaseApiTest<StoreApi> {

  @Override
  public Class<StoreApi> getSutClass() {
    return StoreApi.class;
  }

  @Test
  public void testExample() {
    final RestTemplate restTemplate = new RestTemplate();
    final List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
    messageConverters.add(new MappingJackson2HttpMessageConverter());
    restTemplate.setMessageConverters(messageConverters);

    final StoreApi storeApi =
        SpringRestClientBuilder.create(StoreApi.class)
            .setRestTemplate(restTemplate)
            .setUrl(this.getMockUrl())
            .build();

    storeApi.deleteOrder(1234L);

    this.verify();
  }
}
