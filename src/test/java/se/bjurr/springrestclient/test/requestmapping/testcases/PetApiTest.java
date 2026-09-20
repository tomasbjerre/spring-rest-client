package se.bjurr.springrestclient.test.requestmapping.testcases;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import se.bjurr.springrestclient.test.requestmapping.spec.api.PetApi;
import se.bjurr.springrestclient.test.requestmapping.spec.model.Category;
import se.bjurr.springrestclient.test.requestmapping.spec.model.Pet;
import se.bjurr.springrestclient.test.utils.BaseApiTest;

public class PetApiTest extends BaseApiTest<PetApi> {

  @Override
  public Class<PetApi> getSutClass() {
    return PetApi.class;
  }

  @Test
  public void addPet() {
    this.getSut().addPet(new Pet().category(new Category().id(123L)));
    this.verify(
        """
        [ {
          "body" : "{\\"category\\":{\\"id\\":123,\\"name\\":null},\\"id\\":null,\\"name\\":null,\\"photoUrls\\":[],\\"status\\":null,\\"tags\\":null}",
          "cookies" : { },
          "headers" : {
            "Accept" : "application/json, application/*+json",
            "Connection" : "keep-alive",
            "Content-Type" : "application/json",
            "Transfer-Encoding" : "chunked"
          },
          "method" : "POST",
          "url" : "http://localhost:X/pet"
        } ]""");
  }

  @Test
  public void deletePet() {
    this.getSut().deletePet(123L, "asd");
    this.verify(
        """
        [ {
          "body" : "",
          "cookies" : { },
          "headers" : {
            "Accept" : "application/json, application/*+json",
            "Connection" : "keep-alive",
            "Content-Length" : "0",
            "Content-Type" : "application/x-www-form-urlencoded",
            "api_key" : "asd"
          },
          "method" : "DELETE",
          "url" : "http://localhost:X/pet/123"
        } ]""");
  }

  @Test
  public void findPetsByStatus() {
    this.mockResponse(MediaType.APPLICATION_JSON_VALUE, "[{\"name\":\"a\"}]");

    final List<Pet> actual = this.getSut().findPetsByStatus(Arrays.asList("asdas"));

    final Pet pet = actual.get(0);
    assertThat(pet.getName()).isEqualTo("a");

    this.verify(
        """
        [ {
          "body" : "",
          "cookies" : { },
          "headers" : {
            "Accept" : "application/json",
            "Connection" : "keep-alive"
          },
          "method" : "GET",
          "url" : "http://localhost:X/pet/findByStatus?status=asdas"
        } ]""");
  }

  @Test
  public void findPetsByStatusListMap() {
    this.mockResponse(MediaType.APPLICATION_JSON_VALUE, "[{\"name\":\"a\"}]");

    final List<Map<String, String>> actual =
        this.getSut().findPetsByStatusListMap(Arrays.asList("asdas"));

    final Map<String, String> pet = actual.get(0);
    assertThat(pet.get("name")).isEqualTo("a");

    this.verify(
        """
        [ {
          "body" : "",
          "cookies" : { },
          "headers" : {
            "Accept" : "application/json",
            "Connection" : "keep-alive"
          },
          "method" : "GET",
          "url" : "http://localhost:X/pet/findByStatusListMap?status=asdas"
        } ]""");
  }

  @Test
  public void findPetsByStatusResponseEntity() {
    this.mockResponse(MediaType.APPLICATION_JSON_VALUE, "[{\"name\":\"a\"}]");

    final ResponseEntity<List<Pet>> actual =
        this.getSut().findPetsByStatusResponseEntity(Arrays.asList("asdas"));

    final Pet pet = actual.getBody().get(0);
    assertThat(pet.getName()).isEqualTo("a");
    this.verify(
        """
        [ {
          "body" : "",
          "cookies" : { },
          "headers" : {
            "Accept" : "application/json",
            "Connection" : "keep-alive"
          },
          "method" : "GET",
          "url" : "http://localhost:X/pet/findByStatus?status=asdas"
        } ]""");
  }

  @Test
  public void getPetById() {
    this.mockResponse(MediaType.APPLICATION_JSON_VALUE, "{\"name\":\"a\"}");

    final ResponseEntity<Pet> actual = this.getSut().getPetById(123L);

    assertThat(actual.getBody().getName()).isEqualTo("a");
    this.verify(
        """
        [ {
          "body" : "",
          "cookies" : { },
          "headers" : {
            "Accept" : "application/json",
            "Connection" : "keep-alive"
          },
          "method" : "GET",
          "url" : "http://localhost:X/pet/123"
        } ]""");
  }

  @Test
  public void updatePet() {
    this.getSut().updatePet(new Pet().id(123L));
    this.verify(
        """
        [ {
          "body" : "{\\"category\\":null,\\"id\\":123,\\"name\\":null,\\"photoUrls\\":[],\\"status\\":null,\\"tags\\":null}",
          "cookies" : { },
          "headers" : {
            "Accept" : "application/json, application/*+json",
            "Connection" : "keep-alive",
            "Content-Type" : "application/json",
            "Transfer-Encoding" : "chunked"
          },
          "method" : "PUT",
          "url" : "http://localhost:X/pet"
        } ]""");
  }

  @Test
  public void updatePetWithForm() {
    this.getSut().updatePetWithForm(123L, "asd", "fgfg");
    this.verify(
        """
        [ {
          "body" : "",
          "cookies" : { },
          "headers" : {
            "Accept" : "application/json, application/*+json",
            "Connection" : "keep-alive",
            "Content-Length" : "0",
            "Content-Type" : "application/x-www-form-urlencoded"
          },
          "method" : "POST",
          "url" : "http://localhost:X/pet/123?name=asd&status=fgfg"
        } ]""");
  }

  @Test
  public void uploadFile() {
    this.getSut().uploadFile(123L, "the file");
    this.verify(
        """
        [ {
          "body" : "\\"the file\\"",
          "cookies" : { },
          "headers" : {
            "Accept" : "application/json",
            "Connection" : "keep-alive",
            "Content-Type" : "application/json",
            "Transfer-Encoding" : "chunked"
          },
          "method" : "POST",
          "url" : "http://localhost:X/pet/123/uploadImage"
        } ]""");
  }
}
