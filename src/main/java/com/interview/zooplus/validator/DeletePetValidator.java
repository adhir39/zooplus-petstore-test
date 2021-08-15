package com.interview.zooplus.validator;

import com.interview.zooplus.presentable.PresentableDataContainer;
import lombok.val;
import org.junit.jupiter.api.function.Executable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Component
public class DeletePetValidator implements Validation<PresentableDataContainer> {

    @Override
    public Stream<Executable> validate(PresentableDataContainer validatable) {

        assertTrue(validatable.getDeleteResponse().isDefined(), "Delete pet response is not available for validation");

        //validation for non http 2xx response
        if (Objects.nonNull(validatable.getExpectedNegativeResponse())) {
            return Stream.of(() -> {
                assertTrue(validatable.getDeleteResponse().get().isLeft(), "Delete pet should fail but successfully deleted");

                val actual = validatable.getDeleteResponse().get().getLeft();
                assertAll("validation for negative delete pet response",

                        () -> assertThat(actual.getStatusCode()).isEqualTo(validatable.getExpectedNegativeResponse().getExpectedHttpStatus().value()),
                        () -> {
                            if (Objects.nonNull(validatable.getExpectedNegativeResponse().getExpectedErrorMessage()))
                                assertThat(actual.getResponseBody()).contains(validatable.getExpectedNegativeResponse().getExpectedErrorMessage());
                        });
            });
        }

        //Validation for Http 2XX response
        return Stream.of(() -> {

            assertTrue(validatable.getDeleteResponse().get().isRight(), "Something went wrong during deletion, " +
                    "Http family 2XX expected but received 4XX or 5XX family");

            val actual = validatable.getDeleteResponse().get().get();
            assertAll("validation for add pet",

                    () -> assertThat(actual.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value()),
                    () -> assertThat(actual.getBody().getCode()).isEqualTo(HttpStatus.OK.value()),
                    () -> assertThat(actual.getBody().getMessage()).isEqualTo(validatable.getPetId().get().toString()));
        });
    }
}
