package se.bjurr.springresttemplateclient.test.composed.spec.model;

public class User {
  private final String id;
  private final String firstName;
  private final String lastName;

  public User(final String id, final String firstName, final String lastName) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public String getId() {
    return this.id;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  @Override
  public String toString() {
    return "User [id="
        + this.id
        + ", firstName="
        + this.firstName
        + ", lastName="
        + this.lastName
        + "]";
  }
}
