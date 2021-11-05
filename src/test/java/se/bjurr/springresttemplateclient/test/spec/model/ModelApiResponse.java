package se.bjurr.springresttemplateclient.test.spec.model;

import java.util.Objects;

public class ModelApiResponse {
  private Integer code = null;

  private String type = null;

  private String message = null;

  public ModelApiResponse code(final Integer code) {
    this.code = code;
    return this;
  }

  /**
   * Get code
   *
   * @return code
   */
  public Integer getCode() {
    return this.code;
  }

  public void setCode(final Integer code) {
    this.code = code;
  }

  public ModelApiResponse type(final String type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   *
   * @return type
   */
  public String getType() {
    return this.type;
  }

  public void setType(final String type) {
    this.type = type;
  }

  public ModelApiResponse message(final String message) {
    this.message = message;
    return this;
  }

  /**
   * Get message
   *
   * @return message
   */
  public String getMessage() {
    return this.message;
  }

  public void setMessage(final String message) {
    this.message = message;
  }

  @Override
  public boolean equals(final java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }
    final ModelApiResponse _apiResponse = (ModelApiResponse) o;
    return Objects.equals(this.code, _apiResponse.code)
        && Objects.equals(this.type, _apiResponse.type)
        && Objects.equals(this.message, _apiResponse.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.code, this.type, this.message);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class ModelApiResponse {\n");

    sb.append("    code: ").append(this.toIndentedString(this.code)).append("\n");
    sb.append("    type: ").append(this.toIndentedString(this.type)).append("\n");
    sb.append("    message: ").append(this.toIndentedString(this.message)).append("\n");
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
