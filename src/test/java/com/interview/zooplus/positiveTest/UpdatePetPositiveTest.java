package com.interview.zooplus.positiveTest;

import com.interview.zooplus.TestBase;
import com.interview.zooplus.gateway.representation.PetRepresentation;
import com.interview.zooplus.presentable.Animal;
import com.interview.zooplus.presentable.PresentableDataContainer;
import io.vavr.control.Either;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.interview.zooplus.utility.PetStoreUtility.*;
import static io.vavr.API.Try;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UpdatePetPositiveTest extends TestBase {

    @Test
    public void updatePet_whenCategoryChanged_thenUpdatedSuccessfully() {

        val animal = getPetData(Animal.getRandomAnimal(), null);
        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(animal)
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> container.toBuilder()
                        .petRepresentation(container.getPetRepresentation().toBuilder()
                                .id(container.getPetId().get())
                                .category(container.getPetRepresentation().getCategory().toBuilder()
                                        .id(getRandomId())
                                        .name("Category for update")
                                        .build())
                                .build())
                        .build())
                .map(updatePetUseCase).map(Either::get)
                .map(container -> container.addValidator(updatePetValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void updatePet_whenPhotoUrlsChanged_thenUpdatedSuccessfully() {

        val animal = getPetData(Animal.getRandomAnimal(), null);
        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(animal)
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> container.toBuilder()
                        .petRepresentation(container.getPetRepresentation().toBuilder()
                                .id(container.getPetId().get())
                                .photoUrls(singletonList("https://www.pexels.com/photo/fakepath-to-animal-i736783/"))
                                .build())
                        .build())
                .map(updatePetUseCase).map(Either::get)
                .map(container -> container.addValidator(updatePetValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void updatePet_whenStatusChanged_thenUpdatedSuccessfully() {

        val animal = getPetData(Animal.getRandomAnimal(), null);
        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(animal)
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> container.toBuilder()
                        .petRepresentation(container.getPetRepresentation().toBuilder()
                                .id(container.getPetId().get())
                                .status(getAlternateStatus(animal.getStatus()))
                                .build())
                        .build())
                .map(updatePetUseCase).map(Either::get)
                .map(container -> container.addValidator(updatePetValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void updatePet_whenTagsChanged_thenUpdatedSuccessfully() {

        val animal = getPetData(Animal.getRandomAnimal(), null);
        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(animal)
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> container.toBuilder()
                        .petRepresentation(container.getPetRepresentation().toBuilder()
                                .id(container.getPetId().get())
                                .tags(asList(PetRepresentation.NameIdPair.builder()
                                        .id(getRandomId())
                                        .name("tags for update")
                                        .build()))
                                .build())
                        .build())
                .map(updatePetUseCase).map(Either::get)
                .map(container -> container.addValidator(updatePetValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void updatePet_whenAllFieldsChanged_thenUpdatedSuccessfully() {

        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.getRandomAnimal(), null))
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> container.toBuilder()
                        .petRepresentation(getPetData(Animal.getRandomAnimal(), container.getPetId().get()))
                        .build())
                .map(updatePetUseCase).map(Either::get)
                .map(container -> container.addValidator(updatePetValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void updatePet_whenCategorySetToNull_thenUpdatedSuccessfully() {

        val animal = getPetData(Animal.getRandomAnimal(), null);
        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(animal)
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> container.toBuilder()
                        .petRepresentation(container.getPetRepresentation().toBuilder()
                                .id(container.getPetId().get())
                                .category(null)
                                .build())
                        .build())
                .map(updatePetUseCase).map(Either::get)
                .map(container -> container.addValidator(updatePetValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void updatePet_whenPhotoUrlSetToNull_thenUpdatedSuccessfully() {

        val animal = getPetData(Animal.getRandomAnimal(), null);
        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(animal)
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> container.toBuilder()
                        .petRepresentation(container.getPetRepresentation().toBuilder()
                                .id(container.getPetId().get())
                                .photoUrls(null)
                                .build())
                        .build())
                .map(updatePetUseCase).map(Either::get)
                .map(container -> container.addValidator(updatePetValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void updatePet_whenTagsSetToNull_thenUpdatedSuccessfully() {

        val animal = getPetData(Animal.getRandomAnimal(), null);
        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(animal)
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> container.toBuilder()
                        .petRepresentation(container.getPetRepresentation().toBuilder()
                                .id(container.getPetId().get())
                                .tags(null)
                                .build())
                        .build())
                .map(updatePetUseCase).map(Either::get)
                .map(container -> container.addValidator(updatePetValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void updatePet_whenNameChanged_thenUpdatedSuccessfully() {

        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.cat, null))
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> container.toBuilder()
                        .petRepresentation(container.getPetRepresentation().toBuilder()
                                .id(container.getPetId().get())
                                .name("Catie")
                                .build())
                        .build())
                .map(updatePetUseCase).map(Either::get)
                .map(container -> container.addValidator(updatePetValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }
}
