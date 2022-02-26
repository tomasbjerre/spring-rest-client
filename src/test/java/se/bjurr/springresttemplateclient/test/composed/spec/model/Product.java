package se.bjurr.springresttemplateclient.test.composed.spec.model;

public class Product {
  private final String id;
  private final String name;
  private final Double price;

  public Product(final String id, final String name, final Double price) {
    this.id = id;
    this.name = name;
    this.price = price;
  }

  public String getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public Double getPrice() {
    return this.price;
  }
}
