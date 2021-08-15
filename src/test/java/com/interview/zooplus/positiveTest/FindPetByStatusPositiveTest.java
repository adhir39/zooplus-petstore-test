package com.interview.zooplus.positiveTest;

import com.interview.zooplus.TestBase;
import com.interview.zooplus.gateway.representation.PetRepresentation;
import com.interview.zooplus.presentable.Animal;
import com.interview.zooplus.presentable.PetStatus;
import com.interview.zooplus.presentable.PresentableDataContainer;
import io.vavr.control.Either;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static com.interview.zooplus.utility.PetStoreUtility.getPetData;
import static io.vavr.API.Try;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class FindPetByStatusPositiveTest extends TestBase {

    @Test
    public void findByStatus_whenStatusSold_thenFIndBySoldSuccessful() {

        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.getRandomAnimal(), null).toBuilder().status(PetStatus.sold.name()).build())
                .petStatusToSearchFor(asList(PetStatus.sold.name()))
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> container.toBuilder()
                        .petList(asList(container.getPetRepresentation().toBuilder().id(container.getPetId().get()).build()))
                        .build())
                .map(findPetByStatusUseCase).map(Either::get)
                .map(container -> container.addValidator(findByStatusValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void findByStatus_whenStatusAvailable_thenFIndByAvailableSuccessful() {

        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.getRandomAnimal(), null).toBuilder().status(PetStatus.available.name()).build())
                .petStatusToSearchFor(asList(PetStatus.available.name()))
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> container.toBuilder()
                        .petList(asList(container.getPetRepresentation().toBuilder().id(container.getPetId().get()).build()))
                        .build())
                .map(findPetByStatusUseCase).map(Either::get)
                .map(container -> container.addValidator(findByStatusValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void findByStatus_whenStatusPending_thenFIndByPendingSuccessful() {

        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.getRandomAnimal(), null).toBuilder().status(PetStatus.pending.name()).build())
                .petStatusToSearchFor(asList(PetStatus.pending.name()))
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> container.toBuilder()
                        .petList(asList(container.getPetRepresentation().toBuilder().id(container.getPetId().get()).build()))
                        .build())
                .map(findPetByStatusUseCase).map(Either::get)
                .map(container -> container.addValidator(findByStatusValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void findByStatus_whenMixedStatusSent_thenFIndByStatusSuccessful() {

        val petList = new ArrayList<PetRepresentation>();

        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.getRandomAnimal(), null))
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> {
                    petList.add(container.getPetRepresentation().toBuilder().id(container.getPetId().get()).build());
                    return container.toBuilder().petRepresentation(getPetData(Animal.getRandomAnimal(), null).toBuilder()
                                    .status(PetStatus.pending.name())
                                    .build())
                            .build();
                })
                .map(addPetUseCase).map(Either::get)
                .map(container -> {
                    petList.add(container.getPetRepresentation().toBuilder().id(container.getPetId().get()).build());
                    return container.toBuilder().petRepresentation(getPetData(Animal.getRandomAnimal(), null).toBuilder()
                                    .status(PetStatus.sold.name())
                                    .build())
                            .build();
                })
                .map(addPetUseCase).map(Either::get)
                .map(container -> {
                    petList.add(container.getPetRepresentation().toBuilder().id(container.getPetId().get()).build());
                    return container.toBuilder()
                            .petList(petList)
                            .petStatusToSearchFor(asList(PetStatus.pending.name(), PetStatus.sold.name(), PetStatus.available.name()))
                            .build();
                })
                .map(findPetByStatusUseCase).map(Either::get)
                .map(container -> container.addValidator(findByStatusValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void findByStatus_whenStatusInvalid_thenEmptyResponse() {

        Try(() -> PresentableDataContainer.builder()
                .petStatusToSearchFor(asList(PetStatus.invalid.name()))
                .build())
                .map(findPetByStatusUseCase).map(Either::get)
                .onSuccess(container -> assertAll("assertions for find by status",

                        () -> assertThat(container.getFindByStatusResponse().get().get().getStatusCodeValue()).isEqualTo(200),
                        () -> assertThat(container.getFindByStatusResponse().get().get().getBody()).hasSize(0)))
                .onFailure(Assertions::fail);
    }
}
