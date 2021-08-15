package com.interview.zooplus.negativeTest;

import com.interview.zooplus.TestBase;
import com.interview.zooplus.gateway.representation.UpdatePetFormDataRequestRepresentation;
import com.interview.zooplus.presentable.Animal;
import com.interview.zooplus.presentable.ExpectedNegativeResponse;
import com.interview.zooplus.presentable.PetStatus;
import com.interview.zooplus.presentable.PresentableDataContainer;
import io.vavr.control.Either;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static com.interview.zooplus.utility.PetStoreUtility.getPetData;
import static io.vavr.API.Try;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class DeletePetNegativeTest extends TestBase {

    @Test
    public void deletePet_whenFindByIdAfterDelete_thenHttpStatus404() {

        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.getRandomAnimal(), null))
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(deletePetByIdUseCase).map(Either::get)
                .map(findPetByIdUseCase).map(Either::get)
                .map(container -> container.addValidator(deletePetValidator.validate(container)))
                .map(container -> container.toBuilder()
                        .expectedNegativeResponse(ExpectedNegativeResponse.builder()
                                .expectedHttpStatus(HttpStatus.NOT_FOUND).expectedErrorMessage("Pet not found")
                                .build())
                        .build())
                .map(container -> container.addValidator(findPetByIdValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void deletePet_whenUpdatedAfterDelete_thenHttpStatus404() {

        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.getRandomAnimal(), null))
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(deletePetByIdUseCase).map(Either::get)
                .map(container -> container.toBuilder()
                        .petRepresentation(container.getPetRepresentation().toBuilder()
                                .id(container.getPetId().get())
                                .build())
                        .build())
                .map(updatePetUseCase).map(Either::get)
                .map(container -> container.addValidator(deletePetValidator.validate(container)))
                .map(container -> container.toBuilder()
                        .expectedNegativeResponse(ExpectedNegativeResponse.builder()
                                .expectedHttpStatus(HttpStatus.NOT_FOUND).expectedErrorMessage("Pet not found")
                                .build())
                        .build())
                .map(container -> container.addValidator(updatePetValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void deletePet_whenUpdatedFormDataAfterDelete_thenHttpStatus404() {

        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.getRandomAnimal(), null))
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(deletePetByIdUseCase).map(Either::get)
                .map(container -> container.toBuilder()
                        .updatePetFormDataRequestRepresentation(UpdatePetFormDataRequestRepresentation.builder()
                                .status(PetStatus.sold.name())
                                .build())
                        .build())
                .map(updatePetFormDataByIdUseCase).map(Either::get)
                .map(container -> container.addValidator(deletePetValidator.validate(container)))
                .map(container -> container.toBuilder()
                        .expectedNegativeResponse(ExpectedNegativeResponse.builder()
                                .expectedHttpStatus(HttpStatus.NOT_FOUND).expectedErrorMessage("not found")
                                .build())
                        .build())
                .map(container -> container.addValidator(updatedFormDataValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void deletePet_whenRandomPetIdSent_thenHttpStatus404() {

        Try(() -> PresentableDataContainer.builder().build().putPetId(4387543586429335l))
                .map(deletePetByIdUseCase).map(Either::get)
                .map(container -> container.toBuilder()
                        .expectedNegativeResponse(ExpectedNegativeResponse.builder()
                                .expectedHttpStatus(HttpStatus.NOT_FOUND)
                                .build())
                        .build())
                .map(container -> container.addValidator(deletePetValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }
}
