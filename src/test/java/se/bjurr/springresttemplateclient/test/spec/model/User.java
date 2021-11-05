package se.bjurr.springresttemplateclient.test.spec.model;

import java.util.Objects;

public class User {
  private Long id = null;
  private String username = null;
  private String firstName = null;
  private String lastName = null;
  private String email = null;
  private String password = null;
  private String phone = null;
  private Integer userStatus = null;

  public User id(final Long id) {
    this.id = id;
    return this;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public User username(final String username) {
    this.username = username;
    return this;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(final String username) {
    this.username = username;
  }

  public User firstName(final String firstName) {
    this.firstName = firstName;
    return this;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(final String firstName) {
    this.firstName = firstName;
  }

  public User lastName(final String lastName) {
    this.lastName = lastName;
    return this;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(final String lastName) {
    this.lastName = lastName;
  }

  public User email(final String email) {
    this.email = email;
    return this;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  public User password(final String password) {
    this.password = password;
    return this;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(final String password) {
    this.password = password;
  }

  public User phone(final String phone) {
    this.phone = phone;
    return this;
  }

  public String getPhone() {
    return this.phone;
  }

  public void setPhone(final String phone) {
    this.phone = phone;
  }

  public User userStatus(final Integer userStatus) {
    this.userStatus = userStatus;
    return this;
  }

  public Integer getUserStatus() {
    return this.userStatus;
  }

  public void setUserStatus(final Integer userStatus) {
    this.userStatus = userStatus;
  }

  @Override
  public boolean equals(final java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }
    final User user = (User) o;
    return Objects.equals(this.id, user.id)
        && Objects.equals(this.username, user.username)
        && Objects.equals(this.firstName, user.firstName)
        && Objects.equals(this.lastName, user.lastName)
        && Objects.equals(this.email, user.email)
        && Objects.equals(this.password, user.password)
        && Objects.equals(this.phone, user.phone)
        && Objects.equals(this.userStatus, user.userStatus);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        this.id,
        this.username,
        this.firstName,
        this.lastName,
        this.email,
        this.password,
        this.phone,
        this.userStatus);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class User {\n");

    sb.append("    id: ").append(this.toIndentedString(this.id)).append("\n");
    sb.append("    username: ").append(this.toIndentedString(this.username)).append("\n");
    sb.append("    firstName: ").append(this.toIndentedString(this.firstName)).append("\n");
    sb.append("    lastName: ").append(this.toIndentedString(this.lastName)).append("\n");
    sb.append("    email: ").append(this.toIndentedString(this.email)).append("\n");
    sb.append("    password: ").append(this.toIndentedString(this.password)).append("\n");
    sb.append("    phone: ").append(this.toIndentedString(this.phone)).append("\n");
    sb.append("    userStatus: ").append(this.toIndentedString(this.userStatus)).append("\n");
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
