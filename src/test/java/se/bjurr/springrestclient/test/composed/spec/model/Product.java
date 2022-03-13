package se.bjurr.springrestclient.test.composed.spec.model;

public class Product {
  private String id;
  private String name;
  private Double price;

  public Product() {}

  public Product(final String id, final String name, final Double price) {
    this.id = id;
    this.name = name;
    this.price = price;
  }

  public String getId() {
    return this.id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public Double getPrice() {
    return this.price;
  }

  public void setPrice(final Double price) {
    this.price = price;
  }

  @Override
  public String toString() {
    return "Product [id=" + this.id + ", name=" + this.name + ", price=" + this.price + "]";
  }
}
