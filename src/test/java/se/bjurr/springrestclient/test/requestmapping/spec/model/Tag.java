package se.bjurr.springrestclient.test.requestmapping.spec.model;

import java.util.Objects;

public class Tag {
  private Long id = null;

  private String name = null;

  public Tag id(final Long id) {
    this.id = id;
    return this;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public Tag name(final String name) {
    this.name = name;
    return this;
  }

  public String getName() {
    return this.name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  @Override
  public boolean equals(final java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }
    final Tag tag = (Tag) o;
    return Objects.equals(this.id, tag.id) && Objects.equals(this.name, tag.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.name);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class Tag {\n");

    sb.append("    id: ").append(this.toIndentedString(this.id)).append("\n");
    sb.append("    name: ").append(this.toIndentedString(this.name)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(final java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
