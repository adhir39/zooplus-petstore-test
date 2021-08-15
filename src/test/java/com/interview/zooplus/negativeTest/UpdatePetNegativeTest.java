package com.interview.zooplus.negativeTest;

import com.interview.zooplus.TestBase;
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
public class UpdatePetNegativeTest extends TestBase {

    @Test
    public void UpdatePet_whenPetIdNull_thenHttpStatus400() {

        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.getRandomAnimal(), null))
                .expectedNegativeResponse(ExpectedNegativeResponse.builder()
                        .expectedHttpStatus(HttpStatus.BAD_REQUEST)
                        .build())
                .build())
                .map(updatePetUseCase).map(Either::get)
                .map(container -> container.addValidator(updatePetValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void UpdatePet_whenPetIdRandom_thenHttpStatus404() {

        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.getRandomAnimal(), 1l))
                .expectedNegativeResponse(ExpectedNegativeResponse.builder()
                        .expectedHttpStatus(HttpStatus.NOT_FOUND)
                        .build())
                .build())
                .map(updatePetUseCase).map(Either::get)
                .map(container -> container.addValidator(updatePetValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void UpdatePet_whenPetNameEmpty_thenHttpStatus400() {

        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.getRandomAnimal(), null))
                .expectedNegativeResponse(ExpectedNegativeResponse.builder()
                        .expectedHttpStatus(HttpStatus.BAD_REQUEST)
                        .build())
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> container.toBuilder()
                        .petRepresentation(container.getPetRepresentation().toBuilder()
                                .id(container.getPetId().get())
                                .name("")
                                .build())
                        .build())
                .map(updatePetUseCase).map(Either::get)
                .map(container -> container.addValidator(updatePetValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void UpdatePet_whenPetNameNull_thenHttpStatus400() {

        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.getRandomAnimal(), null))
                .expectedNegativeResponse(ExpectedNegativeResponse.builder()
                        .expectedHttpStatus(HttpStatus.BAD_REQUEST)
                        .build())
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> container.toBuilder()
                        .petRepresentation(container.getPetRepresentation().toBuilder()
                                .id(container.getPetId().get())
                                .name(null)
                                .build())
                        .build())
                .map(updatePetUseCase).map(Either::get)
                .map(container -> container.addValidator(updatePetValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void UpdatePet_whenAllFieldsNull_thenHttpStatus400() {

        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.getRandomAnimal(), null))
                .expectedNegativeResponse(ExpectedNegativeResponse.builder()
                        .expectedHttpStatus(HttpStatus.BAD_REQUEST)
                        .build())
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> container.toBuilder()
                        .petRepresentation(container.getPetRepresentation().toBuilder()
                                .id(container.getPetId().get())
                                .name(null).tags(null).status(null).category(null).photoUrls(null)
                                .build())
                        .build())
                .map(updatePetUseCase).map(Either::get)
                .map(container -> container.addValidator(updatePetValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void UpdatePet_whenAllStatusNull_thenHttpStatus400() {

        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.getRandomAnimal(), null))
                .expectedNegativeResponse(ExpectedNegativeResponse.builder()
                        .expectedHttpStatus(HttpStatus.BAD_REQUEST)
                        .build())
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> container.toBuilder()
                        .petRepresentation(container.getPetRepresentation().toBuilder()
                                .id(container.getPetId().get())
                                .status(null)
                                .build())
                        .build())
                .map(updatePetUseCase).map(Either::get)
                .map(container -> container.addValidator(updatePetValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void UpdatePet_whenStatusInvalid_thenHttpStatus400() {

        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.getRandomAnimal(), null))
                .expectedNegativeResponse(ExpectedNegativeResponse.builder()
                        .expectedHttpStatus(HttpStatus.BAD_REQUEST)
                        .build())
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> container.toBuilder()
                        .petRepresentation(container.getPetRepresentation().toBuilder()
                                .id(container.getPetId().get())
                                .status(PetStatus.invalid.name())
                                .build())
                        .build())
                .map(updatePetUseCase).map(Either::get)
                .map(container -> container.addValidator(updatePetValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }
}
