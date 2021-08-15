package com.interview.zooplus.positiveTest;

import com.interview.zooplus.TestBase;
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
public class AddPetPositiveTest extends TestBase {

    @Test
    public void addPet_whenFullDataSent_thenPetAddedSuccessfully() {

        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.getRandomAnimal(), null))
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> container.addValidator(addPetValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void addPet_whenAnimalCat_thenPetAddedSuccessfully() {

        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.cat, null))
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> container.addValidator(addPetValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void addPet_whenAnimalDog_thenPetAddedSuccessfully() {

        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.dog, null))
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> container.addValidator(addPetValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void addPet_whenAnimalHorse_thenPetAddedSuccessfully() {

        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.horse, null))
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> container.addValidator(addPetValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void addPet_whenCategoryNull_thenPetAddedSuccessfully() {

        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.getRandomAnimal(), null).toBuilder()
                        .category(null)
                        .build())
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> container.addValidator(addPetValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void addPet_whenPhotoUrlNull_thenPetAddedSuccessfully() {

        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.getRandomAnimal(), null).toBuilder()
                        .photoUrls(null)
                        .build())
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> container.addValidator(addPetValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void addPet_whenTagNull_thenPetAddedSuccessfully() {

        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.getRandomAnimal(), null).toBuilder()
                        .tags(null)
                        .build())
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> container.addValidator(addPetValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void addPet_whenNameNull_thenPetAddedSuccessfully() {

        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.getRandomAnimal(), null).toBuilder()
                        .name(null)
                        .build())
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> container.addValidator(addPetValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void addPet_whenStatusPending_thenPetAddedSuccessfully() {

        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.getRandomAnimal(), null).toBuilder()
                        .status(PetStatus.pending.name())
                        .build())
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> container.addValidator(addPetValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void addPet_whenStatusSold_thenPetAddedSuccessfully() {

        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.getRandomAnimal(), null).toBuilder()
                        .status(PetStatus.sold.name())
                        .build())
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> container.addValidator(addPetValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }
}
