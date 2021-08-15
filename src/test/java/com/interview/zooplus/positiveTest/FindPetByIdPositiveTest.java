package com.interview.zooplus.positiveTest;

import com.interview.zooplus.TestBase;
import com.interview.zooplus.presentable.Animal;
import com.interview.zooplus.presentable.PresentableDataContainer;
import io.vavr.control.Either;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.interview.zooplus.utility.PetStoreUtility.getPetData;
import static io.vavr.API.Try;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class FindPetByIdPositiveTest extends TestBase {

    @Test
    public void findPet_whenNewPetAdded_thenFindPetByIdSuccessful() {
        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.getRandomAnimal(), null))
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(findPetByIdUseCase).map(Either::get)
                .map(container -> container.toBuilder()
                        .petRepresentation(container.getPetRepresentation().toBuilder()
                                .id(container.getPetId().get())
                                .build())
                        .build())
                .map(container -> container.addValidator(findPetByIdValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void findPet_whenNewPetAddedWithCategoryNull_thenFindPetByIdSuccessful() {
        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.getRandomAnimal(), null).toBuilder()
                        .category(null)
                        .build())
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(findPetByIdUseCase).map(Either::get)
                .map(container -> container.toBuilder()
                        .petRepresentation(container.getPetRepresentation().toBuilder()
                                .id(container.getPetId().get())
                                .build())
                        .build())
                .map(container -> container.addValidator(findPetByIdValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void findPet_whenNewPetAddedWithPhotoUrlNull_thenFindPetByIdSuccessful() {
        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.getRandomAnimal(), null).toBuilder()
                        .photoUrls(null)
                        .build())
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(findPetByIdUseCase).map(Either::get)
                .map(container -> container.toBuilder()
                        .petRepresentation(container.getPetRepresentation().toBuilder()
                                .id(container.getPetId().get())
                                .build())
                        .build())
                .map(container -> container.addValidator(findPetByIdValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void findPet_whenAllFieldsUpdated_thenFindPetByIdSuccessful() {

        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.getRandomAnimal(), null))
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> container.toBuilder()
                        .petRepresentation(getPetData(Animal.getRandomAnimal(), container.getPetId().get()))
                        .build())
                .map(updatePetUseCase).map(Either::get)
                .map(findPetByIdUseCase).map(Either::get)
                .map(container -> container.addValidator(findPetByIdValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }
}
