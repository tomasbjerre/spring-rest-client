package se.bjurr.springresttemplateclient.test.testcases;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import se.bjurr.springresttemplateclient.test.spec.api.PetApi;
import se.bjurr.springresttemplateclient.test.spec.model.Category;
import se.bjurr.springresttemplateclient.test.spec.model.Pet;
import se.bjurr.springresttemplateclient.test.utils.BaseApiTest;

public class PetApiTest extends BaseApiTest<PetApi> {

  @Override
  public Class<PetApi> getSutClass() {
    return PetApi.class;
  }

  @Test
  public void addPet() {
    this.getSut().addPet(new Pet().category(new Category().id(123L)));
    this.verify();
  }

  @Test
  public void deletePet() {
    this.getSut().deletePet(123L, "asd");
    this.verify();
  }

  @Test
  public void findPetsByStatus() {
    this.mockResponse(MediaType.APPLICATION_JSON_VALUE, "[{\"name\":\"a\"}]");

    final List<Pet> actual = this.getSut().findPetsByStatus(Arrays.asList("asdas"));

    final Pet pet = actual.get(0);
    assertThat(pet.getName()).isEqualTo("a");

    this.verify();
  }

  @Test
  public void findPetsByStatusListMap() {
    this.mockResponse(MediaType.APPLICATION_JSON_VALUE, "[{\"name\":\"a\"}]");

    final List<Map<String, String>> actual =
        this.getSut().findPetsByStatusListMap(Arrays.asList("asdas"));

    final Map<String, String> pet = actual.get(0);
    assertThat(pet.get("name")).isEqualTo("a");

    this.verify();
  }

  @Test
  public void findPetsByStatusResponseEntity() {
    this.mockResponse(MediaType.APPLICATION_JSON_VALUE, "[{\"name\":\"a\"}]");

    final ResponseEntity<List<Pet>> actual =
        this.getSut().findPetsByStatusResponseEntity(Arrays.asList("asdas"));

    final Pet pet = actual.getBody().get(0);
    assertThat(pet.getName()).isEqualTo("a");
    this.verify();
  }

  @Test
  public void getPetById() {
    this.mockResponse(MediaType.APPLICATION_JSON_VALUE, "{\"name\":\"a\"}");

    final ResponseEntity<Pet> actual = this.getSut().getPetById(123L);

    assertThat(actual.getBody().getName()).isEqualTo("a");
    this.verify();
  }

  @Test
  public void updatePet() {
    this.getSut().updatePet(new Pet().id(123L));
    this.verify();
  }

  @Test
  public void updatePetWithForm() {
    this.getSut().updatePetWithForm(123L, "asd", "fgfg");
    this.verify();
  }

  @Test
  public void uploadFile() {
    this.getSut().uploadFile(123L, "the file");
    this.verify();
  }
}
