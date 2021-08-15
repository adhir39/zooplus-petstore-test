package com.interview.zooplus.positiveTest;

import com.interview.zooplus.TestBase;
import com.interview.zooplus.gateway.representation.UpdatePetFormDataRequestRepresentation;
import com.interview.zooplus.presentable.Animal;
import com.interview.zooplus.presentable.PetStatus;
import com.interview.zooplus.presentable.PresentableDataContainer;
import io.vavr.control.Either;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.interview.zooplus.utility.PetStoreUtility.getPetData;
import static io.vavr.API.Try;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class DeletePetPositiveTest extends TestBase {

    @Test
    public void deletePet_whenValidPetIdSent_thenPetDeletedSuccessfully() {

        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.getRandomAnimal(), null))
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(deletePetByIdUseCase).map(Either::get)
                .map(container -> container.addValidator(deletePetValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void deletePet_whenUpdatedBeforeDeletion_thenPetDeletedSuccessfully() {

        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.getRandomAnimal(), null))
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> container.toBuilder()
                        .petRepresentation(container.getPetRepresentation().toBuilder()
                                .id(container.getPetId().get()).name("very nice Elephant")
                                .build())
                        .build())
                .map(updatePetUseCase).map(Either::get)
                .map(deletePetByIdUseCase).map(Either::get)
                .map(container -> container.addValidator(deletePetValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void deletePet_whenUpdatedFormDataBeforeDeletion_thenPetDeletedSuccessfully() {

        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.getRandomAnimal(), null))
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> container.toBuilder()
                        .updatePetFormDataRequestRepresentation(UpdatePetFormDataRequestRepresentation.builder()
                                .status(PetStatus.pending.name())
                                .build())
                        .build())
                .map(updatePetFormDataByIdUseCase).map(Either::get)
                .map(deletePetByIdUseCase).map(Either::get)
                .map(container -> container.addValidator(deletePetValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }
}
