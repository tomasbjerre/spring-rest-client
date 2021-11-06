package se.bjurr.springresttemplateclient.test.utils;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.stubbing.ServeEvent;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import java.util.Arrays;
import java.util.List;
import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.approvaltests.reporters.AutoApproveReporter;
import org.junit.After;
import org.junit.Before;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import se.bjurr.springresttemplateclient.SpringRestTemplateClientBuilder;

public abstract class BaseApiTest<T> {

  private WireMockServer wiremock;

  @Before
  public void baseBefore() {
    final WireMockConfiguration configuration =
        WireMockConfiguration //
            .options()
            .dynamicPort()
            .notifier(new ConsoleNotifier(true));
    this.wiremock = new WireMockServer(configuration);
    this.wiremock.start();

    this.mockResponse(MediaType.APPLICATION_JSON_VALUE, "{}");
  }

  @After
  public void after() {
    this.wiremock.shutdown();
  }

  public void mockResponse(final String mediaType, final String body) {
    for (final StubMapping sm : this.wiremock.getStubMappings()) {
      this.wiremock.removeStub(sm);
    }

    final MappingBuilder anythingIsOk =
        WireMock //
            .any(WireMock.anyUrl()) //
            .willReturn(WireMock.okForContentType(mediaType, body));
    this.wiremock.stubFor(anythingIsOk);
  }

  public void verify() {
    final List<ServeEvent> storedEvents = this.getStoredEvents();

    final String actual = WiremockInvocation.from(storedEvents);

    Approvals.verify(actual, new Options().withReporter(new AutoApproveReporter()));
  }

  public abstract Class<T> getSutClass();

  public T getSut() {
    return SpringRestTemplateClientBuilder.create(this.getSutClass())
        .setRestTemplate(this.restTemplate())
        .setUrl(this.getMockUrl())
        .build();
  }

  public String getMockUrl() {
    return "http://localhost:" + this.wiremock.port();
  }

  private RestTemplate restTemplate() {
    final RestTemplate restTemplate = new RestTemplate();
    restTemplate.setMessageConverters(
        Arrays.asList(new MappingJackson2HttpMessageConverter(), new MappingTextPlain()));
    return restTemplate;
  }

  private List<ServeEvent> getStoredEvents() {
    final List<ServeEvent> serverEvents = this.wiremock.getAllServeEvents();
    assertThat(serverEvents).isNotEmpty();
    return serverEvents;
  }
}
