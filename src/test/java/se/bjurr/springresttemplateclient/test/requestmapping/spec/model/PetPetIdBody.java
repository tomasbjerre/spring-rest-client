package se.bjurr.springresttemplateclient.test.requestmapping.spec.model;

import java.util.Objects;

public class PetPetIdBody {
  private String name = null;

  private String status = null;

  public PetPetIdBody name(final String name) {
    this.name = name;
    return this;
  }

  public String getName() {
    return this.name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public PetPetIdBody status(final String status) {
    this.status = status;
    return this;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(final String status) {
    this.status = status;
  }

  @Override
  public boolean equals(final java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }
    final PetPetIdBody petPetIdBody = (PetPetIdBody) o;
    return Objects.equals(this.name, petPetIdBody.name)
        && Objects.equals(this.status, petPetIdBody.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.name, this.status);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class PetPetIdBody {\n");

    sb.append("    name: ").append(this.toIndentedString(this.name)).append("\n");
    sb.append("    status: ").append(this.toIndentedString(this.status)).append("\n");
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
