package com.interview.zooplus.negativeTest;

import com.interview.zooplus.TestBase;
import com.interview.zooplus.presentable.Animal;
import com.interview.zooplus.presentable.ExpectedNegativeResponse;
import com.interview.zooplus.presentable.PetStatus;
import com.interview.zooplus.presentable.PresentableDataContainer;
import io.vavr.control.Either;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static com.interview.zooplus.utility.PetStoreUtility.getPetData;
import static io.vavr.API.Try;
import static java.util.Collections.singletonList;


/*  As it is not clear from the API documentation which fields are mandatory,
      I assumed if some fields are mandatory if provided and hence adding
      a pet should fail with some http 4XX family. For this test project we assume it would be http 400.
   */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AddPetNegativeTest extends TestBase {


    @Test
    public void addPet_whenCategoryIdNull_thenHttpStatus400() {

        val request = getPetData(Animal.getRandomAnimal(), null);

        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(request.toBuilder()
                        .category(request.getCategory()
                                .toBuilder()
                                .id(null) //assumption : if category is given then id should not be null
                                .build())
                        .build())
                .expectedNegativeResponse(ExpectedNegativeResponse.builder()
                        .expectedHttpStatus(HttpStatus.BAD_REQUEST)
                        .build())
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> container.addValidator(addPetValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void addPet_whenTagIdNull_thenHttpStatus400() {

        val request = getPetData(Animal.getRandomAnimal(), null);

        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(request.toBuilder()
                        .tags(singletonList(request.getTags().get(0)
                                .toBuilder()
                                .id(null) //assumption : if tag is given then id should not be null
                                .build()))
                        .build())
                .expectedNegativeResponse(ExpectedNegativeResponse.builder()
                        .expectedHttpStatus(HttpStatus.BAD_REQUEST)
                        .build())
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> container.addValidator(addPetValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void addPet_whenIdNotNull_thenHttpStatus400() {

        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.getRandomAnimal(), null).toBuilder()
                        .id(6986656645l) // pet id should be null during creation
                        .build())
                .expectedNegativeResponse(ExpectedNegativeResponse.builder()
                        .expectedHttpStatus(HttpStatus.BAD_REQUEST)
                        .build())
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> container.addValidator(addPetValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void addPet_whenStatusInvalid_thenHttpStatus400() {

        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.getRandomAnimal(), null).toBuilder()
                        .status(PetStatus.invalid.name())
                        .build())
                .expectedNegativeResponse(ExpectedNegativeResponse.builder()
                        .expectedHttpStatus(HttpStatus.BAD_REQUEST)
                        .build())
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> container.addValidator(addPetValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }


    @Test
    public void addPet_whenNameIsNull_thenHttpStatus400() {

        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.getRandomAnimal(), null).toBuilder()
                        .name(null) // pet id should be null during creation
                        .build())
                .expectedNegativeResponse(ExpectedNegativeResponse.builder()
                        .expectedHttpStatus(HttpStatus.BAD_REQUEST)
                        .build())
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> container.addValidator(addPetValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void addPet_whenNameIsEmpty_thenHttpStatus400() {

        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.getRandomAnimal(), null).toBuilder()
                        .name("")
                        .build())
                .expectedNegativeResponse(ExpectedNegativeResponse.builder()
                        .expectedHttpStatus(HttpStatus.BAD_REQUEST)
                        .build())
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> container.addValidator(addPetValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void addPet_whenStatusNull_thenHttpStatus400() {

        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.getRandomAnimal(), null).toBuilder()
                        .status(null)
                        .build())
                .expectedNegativeResponse(ExpectedNegativeResponse.builder()
                        .expectedHttpStatus(HttpStatus.BAD_REQUEST)
                        .build())
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(container -> container.addValidator(addPetValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }
}
