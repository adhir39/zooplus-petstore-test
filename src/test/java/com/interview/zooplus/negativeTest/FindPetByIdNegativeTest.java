package com.interview.zooplus.negativeTest;

import com.interview.zooplus.TestBase;
import com.interview.zooplus.presentable.ExpectedNegativeResponse;
import com.interview.zooplus.presentable.PresentableDataContainer;
import io.vavr.control.Either;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static io.vavr.API.Try;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class FindPetByIdNegativeTest extends TestBase {

    @Test
    public void findPet_whenPetIdRandom_thenHttpStatus404() {
        Try(() -> PresentableDataContainer.builder()
                .expectedNegativeResponse(ExpectedNegativeResponse.builder()
                        .expectedHttpStatus(HttpStatus.NOT_FOUND)
                        .expectedErrorMessage("Pet not found")
                        .build())
                .build().putPetId(24355676457543l))
                .map(findPetByIdUseCase).map(Either::get)
                .map(container -> container.addValidator(findPetByIdValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Throwable::printStackTrace)
                .onFailure(Assertions::fail);
    }
}
