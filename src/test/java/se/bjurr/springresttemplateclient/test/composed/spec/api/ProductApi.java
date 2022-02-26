package se.bjurr.springresttemplateclient.test.composed.spec.api;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import se.bjurr.springresttemplateclient.test.composed.spec.model.Product;
import se.bjurr.springresttemplateclient.test.composed.spec.model.ProductInput;

@RestController
public interface ProductApi {

  @GetMapping("/product/{id}")
  Product getProduct(@PathVariable("id") String id);

  @DeleteMapping("/product/{id}")
  void deleteProduct(@PathVariable("id") String id);

  @PutMapping("/product/{id}")
  Product updateProduct(@PathVariable("id") String id, @RequestBody ProductInput input);

  @PostMapping("/product")
  Product addProduct(@RequestBody ProductInput input);
}
