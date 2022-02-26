package se.bjurr.springresttemplateclient.test.composed.spec.model;

public class ProductInput {
  private Integer id;

  public ProductInput() {}

  public ProductInput(final Integer id) {
    this.id = id;
  }

  public void setId(final Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return this.id;
  }

  @Override
  public String toString() {
    return "ProductInput [id=" + this.id + "]";
  }
}
