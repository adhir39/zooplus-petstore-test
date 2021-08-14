package com.interview.zooplus.validator;

import com.interview.zooplus.gateway.representation.PetRepresentation;
import com.interview.zooplus.presentable.PresentableDataContainer;
import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.*;

import lombok.val;
import org.junit.jupiter.api.function.Executable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Stream;

@Component
public class AddPetValidator implements Validation<PresentableDataContainer> {

    @Override
    public Stream<Executable> validate(PresentableDataContainer validatable) {

        assertTrue(validatable.getAddPetResponse().isDefined(), "Add pet response is not available for validation");

        //validation for not http 2xx response
        if(Objects.nonNull(validatable.getExpectedNegativeResponse())) {
            return Stream.of(() -> {
                assertTrue(validatable.getAddPetResponse().get().isLeft(), "adding pet should fail but successfully added");

                val actual = validatable.getAddPetResponse().get().getLeft();
                assertAll("validation for negative add pet response",

                        () -> assertThat(actual.getStatusCode()).isEqualTo(validatable.getExpectedNegativeResponse().getExpectedHttpStatus().value()),
                        () -> {
                            if (Objects.nonNull(validatable.getExpectedNegativeResponse().getExpectedErrorMessage()))
                                assertThat(actual.getResponseBody()).contains(validatable.getExpectedNegativeResponse().getExpectedErrorMessage());
                        });
            });
        }

        //Validation for Http 2XX response
        return Stream.of( () -> {

            assertTrue(validatable.getAddPetResponse().get().isRight(), "adding pet was unsuccessful");

            val actual = validatable.getAddPetResponse().get().get();
            assertAll("validation for add pet",

                    () -> assertThat(actual.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value()),
                    () -> assertThat(actual.getBody().getId()).isNotNull(),
                    () -> assertThat(actual.getBody()).isEqualToIgnoringGivenFields(validatable.getPetRepresentation(),"id"));
        });
    }
}
