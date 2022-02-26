package se.bjurr.springresttemplateclient.test.composed.spec.api;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.bjurr.springresttemplateclient.test.composed.spec.model.Product;
import se.bjurr.springresttemplateclient.test.composed.spec.model.ProductInput;

@RestController
@RequestMapping("/product")
public interface ProductApi {

  @GetMapping("/{id}")
  Product getProduct(@PathVariable String id);

  @DeleteMapping("/{id}")
  void deleteProduct(@PathVariable String id);

  @PutMapping("/{id}")
  Product updateProduct(@PathVariable String id, @RequestBody ProductInput input);

  @PostMapping
  Product addProduct(@RequestBody ProductInput input);
}
