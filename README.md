# Spring Rest Client

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/se.bjurr.springrestclient/spring-rest-client/badge.svg)](https://maven-badges.herokuapp.com/maven-central/se.bjurr.springrestclient/spring-rest-client)

Dynamically create Spring (proxy class) client from annotated interface. Very lightweight, only depends on `spring-web:4.3`, compared to alternatives like [Feign](https://github.com/OpenFeign/feign).

Given `MyApiInterface` is a Spring-annotated Java interface.

```java
final MyApiInterface myClient = SpringRestClientBuilder
  .create(MyApiInterface.class)
  .setUrl(this.getMockUrl())
  .setRestTemplate(restTemplate)         // Optional
  .setHeader("header-name", "the value") // Optional
  .setHeaders(HttpHeaders)               // Optional
  .build();
```

Here `myClient` is a dynamically created [Java Proxy object](https://docs.oracle.com/javase/7/docs/api/java/lang/reflect/Proxy.html).

```java
final ResponseEntity<MyDTO> response = myClient.getMyDto();
```

The method invocation (`getMyDto()`) on that object (`myClient`) will be translated to a HTTP request using the annotations of that method in that interface. And `response` will be the result of that HTTP request.

Supports both signatures:

- `ResponseEntity<X> deleteOrder(Long)`
- `X deleteOrder(Long)`

See also [test cases](/src/test/java/se/bjurr/springrestclient/test).
