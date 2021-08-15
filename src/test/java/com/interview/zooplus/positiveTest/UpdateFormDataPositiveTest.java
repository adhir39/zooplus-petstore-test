package com.interview.zooplus.positiveTest;

import com.interview.zooplus.TestBase;
import com.interview.zooplus.gateway.representation.UpdatePetFormDataRequestRepresentation;
import com.interview.zooplus.presentable.Animal;
import com.interview.zooplus.presentable.PetStatus;
import com.interview.zooplus.presentable.PresentableDataContainer;
import io.vavr.control.Either;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.interview.zooplus.utility.PetStoreUtility.getPetData;
import static io.vavr.API.Try;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UpdateFormDataPositiveTest extends TestBase {

    @Test
    public void UpdateFormData_whenNameChanged_thenUpdatedSuccessfully() {

        val updatedName = "Best cat ever";
        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.cat, null))
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> container.toBuilder()
                        .updatePetFormDataRequestRepresentation(UpdatePetFormDataRequestRepresentation.builder()
                                .name(updatedName)
                                .build())
                        .petRepresentation(container.getPetRepresentation().toBuilder()
                                .id(container.getPetId().get())
                                .name(updatedName)
                                .build())
                        .build())
                .map(updatePetFormDataByIdUseCase).map(Either::get)
                .map(findPetByIdUseCase).map(Either::get)
                .map(container -> container.addValidator(updatedFormDataValidator.validate(container))
                        .addValidator(findPetByIdValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void UpdateFormData_whenStatusChanged_thenUpdatedSuccessfully() {

        val updatedStatus = PetStatus.pending.name();
        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.getRandomAnimal(), null).toBuilder()
                        .status(PetStatus.available.name())
                        .build())
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> container.toBuilder()
                        .updatePetFormDataRequestRepresentation(UpdatePetFormDataRequestRepresentation.builder()
                                .status(updatedStatus)
                                .build())
                        .petRepresentation(container.getPetRepresentation().toBuilder()
                                .id(container.getPetId().get())
                                .status(updatedStatus)
                                .build())
                        .build())
                .map(updatePetFormDataByIdUseCase).map(Either::get)
                .map(findPetByIdUseCase).map(Either::get)
                .map(container -> container.addValidator(updatedFormDataValidator.validate(container))
                        .addValidator(findPetByIdValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void UpdateFormData_whenBothFieldChanged_thenUpdatedSuccessfully() {

        val updatedStatus = PetStatus.available.name();
        val updatedName = "German Shepard";
        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.dog, null).toBuilder()
                        .status(PetStatus.sold.name())
                        .build())
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> container.toBuilder()
                        .updatePetFormDataRequestRepresentation(UpdatePetFormDataRequestRepresentation.builder()
                                .status(updatedStatus).name(updatedName)
                                .build())
                        .petRepresentation(container.getPetRepresentation().toBuilder()
                                .id(container.getPetId().get())
                                .status(updatedStatus).name(updatedName)
                                .build())
                        .build())
                .map(updatePetFormDataByIdUseCase).map(Either::get)
                .map(findPetByIdUseCase).map(Either::get)
                .map(container -> container.addValidator(updatedFormDataValidator.validate(container))
                        .addValidator(findPetByIdValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }
}
