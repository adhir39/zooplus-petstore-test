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
public class UpdateFormDataNegativeTest extends TestBase {

    @Test
    public void UpdateFormData_whenNameNull_thenHttpStatus400() {

        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.getRandomAnimal(), null))
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> container.toBuilder()
                        .updatePetFormDataRequestRepresentation(UpdatePetFormDataRequestRepresentation.builder()
                                .name(null)
                                .build())
                        .expectedNegativeResponse(ExpectedNegativeResponse.builder()
                                .expectedHttpStatus(HttpStatus.BAD_REQUEST)
                                .build())
                        .build())
                .map(updatePetFormDataByIdUseCase).map(Either::get)
                .map(container -> container.addValidator(updatedFormDataValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void UpdateFormData_whenNameEmpty_thenHttpStatus400() {

        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.getRandomAnimal(), null))
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> container.toBuilder()
                        .updatePetFormDataRequestRepresentation(UpdatePetFormDataRequestRepresentation.builder()
                                .name("")
                                .build())
                        .expectedNegativeResponse(ExpectedNegativeResponse.builder()
                                .expectedHttpStatus(HttpStatus.BAD_REQUEST)
                                .build())
                        .build())
                .map(updatePetFormDataByIdUseCase).map(Either::get)
                .map(container -> container.addValidator(updatedFormDataValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void UpdateFormData_whenStatusNull_thenHttpStatus400() {

        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.getRandomAnimal(), null))
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> container.toBuilder()
                        .updatePetFormDataRequestRepresentation(UpdatePetFormDataRequestRepresentation.builder()
                                .status(null)
                                .build())
                        .expectedNegativeResponse(ExpectedNegativeResponse.builder()
                                .expectedHttpStatus(HttpStatus.BAD_REQUEST)
                                .build())
                        .build())
                .map(updatePetFormDataByIdUseCase).map(Either::get)
                .map(container -> container.addValidator(updatedFormDataValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void UpdateFormData_whenStatusEmpty_thenHttpStatus400() {

        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.getRandomAnimal(), null))
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> container.toBuilder()
                        .updatePetFormDataRequestRepresentation(UpdatePetFormDataRequestRepresentation.builder()
                                .status("")
                                .build())
                        .expectedNegativeResponse(ExpectedNegativeResponse.builder()
                                .expectedHttpStatus(HttpStatus.BAD_REQUEST)
                                .build())
                        .build())
                .map(updatePetFormDataByIdUseCase).map(Either::get)
                .map(container -> container.addValidator(updatedFormDataValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void UpdateFormData_whenStatusInvalid_thenHttpStatus400() {

        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.getRandomAnimal(), null))
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> container.toBuilder()
                        .updatePetFormDataRequestRepresentation(UpdatePetFormDataRequestRepresentation.builder()
                                .status(PetStatus.invalid.name())
                                .build())
                        .expectedNegativeResponse(ExpectedNegativeResponse.builder()
                                .expectedHttpStatus(HttpStatus.BAD_REQUEST)
                                .build())
                        .build())
                .map(updatePetFormDataByIdUseCase).map(Either::get)
                .map(container -> container.addValidator(updatedFormDataValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void UpdateFormData_whenPetIdRandom_thenHttpStatus404() {

        Try(() -> PresentableDataContainer.builder()
                .build().putPetId(9456849452395l))
                .map(container -> container.toBuilder()
                        .updatePetFormDataRequestRepresentation(UpdatePetFormDataRequestRepresentation.builder()
                                .status(PetStatus.sold.name())
                                .build())
                        .expectedNegativeResponse(ExpectedNegativeResponse.builder()
                                .expectedHttpStatus(HttpStatus.NOT_FOUND).expectedErrorMessage("not found")
                                .build())
                        .build())
                .map(updatePetFormDataByIdUseCase).map(Either::get)
                .map(container -> container.addValidator(updatedFormDataValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }
}
