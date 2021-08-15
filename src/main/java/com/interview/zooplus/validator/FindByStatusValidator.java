package com.interview.zooplus.validator;

import com.interview.zooplus.gateway.representation.PetRepresentation;
import com.interview.zooplus.presentable.PetStatus;
import com.interview.zooplus.presentable.PresentableDataContainer;
import lombok.val;
import org.junit.jupiter.api.function.Executable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Component
public class FindByStatusValidator implements Validation<PresentableDataContainer> {

    @Override
    public Stream<Executable> validate(PresentableDataContainer validatable) {
        assertTrue(validatable.getFindByStatusResponse().isDefined(), "find by statust response is not available for validation");

        //validation for non http 2xx response
        if (Objects.nonNull(validatable.getExpectedNegativeResponse())) {
            return Stream.of(() -> {
                assertTrue(validatable.getAddPetResponse().get().isLeft(), "find  pet by status should fail but successfully fetched");

                val actual = validatable.getAddPetResponse().get().getLeft();
                assertAll("validation for negative find pet by status",

                        () -> assertThat(actual.getStatusCode()).isEqualTo(validatable.getExpectedNegativeResponse().getExpectedHttpStatus().value()),
                        () -> {
                            if (Objects.nonNull(validatable.getExpectedNegativeResponse().getExpectedErrorMessage()))
                                assertThat(actual.getResponseBody()).contains(validatable.getExpectedNegativeResponse().getExpectedErrorMessage());
                        });
            });
        }

        //Validation for Http 2XX response
        return Stream.of(() -> {

            assertTrue(validatable.getFindByStatusResponse().get().isRight(), "Something went wrong during find pet by status, " +
                    "Http family 2XX expected but received 4XX or 5XX family");

            val actual = validatable.getFindByStatusResponse().get().get();
            PetRepresentation[] arrayPet = new PetRepresentation[validatable.getPetList().size()];
            assertAll("validation for add pet",

                    () -> assertThat(actual.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value()),
                    () -> assertThat(actual.getBody()).isNotEmpty().hasSizeGreaterThan(0),
                    () -> assertThat(actual.getBody()).contains(validatable.getPetList().toArray(arrayPet)),
                    () -> assertThat(actual.getBody()
                            .parallelStream()
                            .map(PetRepresentation::getStatus)
                            .distinct()
                            .map(PetStatus::valueOf)
                            .collect(Collectors.toList())).isEqualTo(validatable.getPetStatusToSearchFor()));
        });
    }
}
