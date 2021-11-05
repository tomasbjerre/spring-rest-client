# Spring RestTemplate Client

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/se.bjurr.springresttemplate/spring-resttemplate-client/badge.svg)](https://maven-badges.herokuapp.com/maven-central/se.bjurr.springresttemplate/spring-resttemplate-client)

Dynamically create Spring RestTemplate (proxy class) client from annotated interface.

Given `MyApiInterface` is a Spring-annotated Java interface.

```java
final MyApiInterface myClient = SpringRestTemplateClientBuilder
  .create(MyApiInterface.class)
  .setUrl("http://...")
  .build();
```

Here `myClient` is a dynamically created [Java Proxy object](https://docs.oracle.com/javase/7/docs/api/java/lang/reflect/Proxy.html).

```java
final ResponseEntity<MyDTO> response = myClient.getMyDto();
```

The method invocation (`getMyDto()`) on that object (`myClient`) will be translated to a HTTP request using the annotations of that method in that interface. And `response` will be the result of that HTTP request.

