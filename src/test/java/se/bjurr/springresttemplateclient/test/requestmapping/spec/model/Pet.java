package se.bjurr.springresttemplateclient.test.requestmapping.spec.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Pet {
  private Long id = null;

  private Category category = null;

  private String name = null;

  private List<String> photoUrls = new ArrayList<>();

  private List<Tag> tags = null;

  /** pet status in the store */
  public enum StatusEnum {
    AVAILABLE("available"),

    PENDING("pending"),

    SOLD("sold");

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

  public Pet id(final Long id) {
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

  public Pet category(final Category category) {
    this.category = category;
    return this;
  }

  /**
   * Get category
   *
   * @return category
   */
  public Category getCategory() {
    return this.category;
  }

  public void setCategory(final Category category) {
    this.category = category;
  }

  public Pet name(final String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   *
   * @return name
   */
  public String getName() {
    return this.name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public Pet photoUrls(final List<String> photoUrls) {
    this.photoUrls = photoUrls;
    return this;
  }

  public Pet addPhotoUrlsItem(final String photoUrlsItem) {
    this.photoUrls.add(photoUrlsItem);
    return this;
  }

  /**
   * Get photoUrls
   *
   * @return photoUrls
   */
  public List<String> getPhotoUrls() {
    return this.photoUrls;
  }

  public void setPhotoUrls(final List<String> photoUrls) {
    this.photoUrls = photoUrls;
  }

  public Pet tags(final List<Tag> tags) {
    this.tags = tags;
    return this;
  }

  public Pet addTagsItem(final Tag tagsItem) {
    if (this.tags == null) {
      this.tags = new ArrayList<>();
    }
    this.tags.add(tagsItem);
    return this;
  }

  /**
   * Get tags
   *
   * @return tags
   */
  public List<Tag> getTags() {
    return this.tags;
  }

  public void setTags(final List<Tag> tags) {
    this.tags = tags;
  }

  public Pet status(final StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * pet status in the store
   *
   * @return status
   */
  public StatusEnum getStatus() {
    return this.status;
  }

  public void setStatus(final StatusEnum status) {
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
    final Pet pet = (Pet) o;
    return Objects.equals(this.id, pet.id)
        && Objects.equals(this.category, pet.category)
        && Objects.equals(this.name, pet.name)
        && Objects.equals(this.photoUrls, pet.photoUrls)
        && Objects.equals(this.tags, pet.tags)
        && Objects.equals(this.status, pet.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.category, this.name, this.photoUrls, this.tags, this.status);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class Pet {\n");

    sb.append("    id: ").append(this.toIndentedString(this.id)).append("\n");
    sb.append("    category: ").append(this.toIndentedString(this.category)).append("\n");
    sb.append("    name: ").append(this.toIndentedString(this.name)).append("\n");
    sb.append("    photoUrls: ").append(this.toIndentedString(this.photoUrls)).append("\n");
    sb.append("    tags: ").append(this.toIndentedString(this.tags)).append("\n");
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
