package se.bjurr.springresttemplateclient.test.testcases;

import java.util.Arrays;
import org.junit.Test;
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

  // @Test
  public void findPetsByStatus() {
    this.getSut().findPetsByStatus(Arrays.asList("asdas"));
    this.verify();
  }

  // @Test
  public void findPetsByTags() {
    this.getSut().findPetsByTags(Arrays.asList("asdas"));
    this.verify();
  }

  @Test
  public void getPetById() {
    this.getSut().getPetById(123L);
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
