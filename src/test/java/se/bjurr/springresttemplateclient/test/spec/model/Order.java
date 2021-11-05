package se.bjurr.springresttemplateclient.test.spec.model;

import java.time.OffsetDateTime;
import java.util.Objects;

public class Order {
  private Long id = null;

  private Long petId = null;

  private Integer quantity = null;

  private OffsetDateTime shipDate = null;

  /** Order Status */
  public enum StatusEnum {
    PLACED("placed"),

    APPROVED("approved"),

    DELIVERED("delivered");

    private final String value;

    StatusEnum(final String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return String.valueOf(this.value);
    }

    public static StatusEnum fromValue(final String text) {
      for (final StatusEnum b : StatusEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  private StatusEnum status = null;

  private Boolean complete = false;

  public Order id(final Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   *
   * @return id
   */
  public Long getId() {
    return this.id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public Order petId(final Long petId) {
    this.petId = petId;
    return this;
  }

  /**
   * Get petId
   *
   * @return petId
   */
  public Long getPetId() {
    return this.petId;
  }

  public void setPetId(final Long petId) {
    this.petId = petId;
  }

  public Order quantity(final Integer quantity) {
    this.quantity = quantity;
    return this;
  }

  /**
   * Get quantity
   *
   * @return quantity
   */
  public Integer getQuantity() {
    return this.quantity;
  }

  public void setQuantity(final Integer quantity) {
    this.quantity = quantity;
  }

  public Order shipDate(final OffsetDateTime shipDate) {
    this.shipDate = shipDate;
    return this;
  }

  /**
   * Get shipDate
   *
   * @return shipDate
   */
  public OffsetDateTime getShipDate() {
    return this.shipDate;
  }

  public void setShipDate(final OffsetDateTime shipDate) {
    this.shipDate = shipDate;
  }

  public Order status(final StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * Order Status
   *
   * @return status
   */
  public StatusEnum getStatus() {
    return this.status;
  }

  public void setStatus(final StatusEnum status) {
    this.status = status;
  }

  public Order complete(final Boolean complete) {
    this.complete = complete;
    return this;
  }

  /**
   * Get complete
   *
   * @return complete
   */
  public Boolean isComplete() {
    return this.complete;
  }

  public void setComplete(final Boolean complete) {
    this.complete = complete;
  }

  @Override
  public boolean equals(final java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }
    final Order order = (Order) o;
    return Objects.equals(this.id, order.id)
        && Objects.equals(this.petId, order.petId)
        && Objects.equals(this.quantity, order.quantity)
        && Objects.equals(this.shipDate, order.shipDate)
        && Objects.equals(this.status, order.status)
        && Objects.equals(this.complete, order.complete);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        this.id, this.petId, this.quantity, this.shipDate, this.status, this.complete);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class Order {\n");

    sb.append("    id: ").append(this.toIndentedString(this.id)).append("\n");
    sb.append("    petId: ").append(this.toIndentedString(this.petId)).append("\n");
    sb.append("    quantity: ").append(this.toIndentedString(this.quantity)).append("\n");
    sb.append("    shipDate: ").append(this.toIndentedString(this.shipDate)).append("\n");
    sb.append("    status: ").append(this.toIndentedString(this.status)).append("\n");
    sb.append("    complete: ").append(this.toIndentedString(this.complete)).append("\n");
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
