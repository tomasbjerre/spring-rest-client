package se.bjurr.springresttemplateclient.test.composed.spec.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.bjurr.springresttemplateclient.test.composed.spec.model.User;

@RestController
@RequestMapping("/user")
public interface UserApi {

  @GetMapping("/{id}")
  User getUser(@PathVariable final String id);
}
