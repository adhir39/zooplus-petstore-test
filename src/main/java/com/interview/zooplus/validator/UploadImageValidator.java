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
public class UploadImageValidator implements Validation<PresentableDataContainer> {


    @Override
    public Stream<Executable> validate(PresentableDataContainer validatable) {
        assertTrue(validatable.getUploadImageResponse().isDefined(), "Upload pet image response is not available for validation");

        //validation for non http 2xx response
        if (Objects.nonNull(validatable.getExpectedNegativeResponse())) {
            return Stream.of(() -> {
                assertTrue(validatable.getUploadImageResponse().get().isLeft(), "uploading pet image should fail but successfully uploaded");

                val actual = validatable.getUploadImageResponse().get().getLeft();
                assertAll("validation for negative update pet response",

                        () -> assertThat(actual.getStatusCode()).isEqualTo(validatable.getExpectedNegativeResponse().getExpectedHttpStatus().value()),
                        () -> {
                            if (Objects.nonNull(validatable.getExpectedNegativeResponse().getExpectedErrorMessage()))
                                assertThat(actual.getResponseBody()).contains(validatable.getExpectedNegativeResponse().getExpectedErrorMessage());
                        });
            });
        }

        //Validation for Http 2XX response
        return Stream.of(() -> {

            assertTrue(validatable.getUploadImageResponse().get().isRight(), "Something went wrong during uploading image, " +
                    "Http family 2XX expected but received 4XX or 5XX family");

            val actual = validatable.getUploadImageResponse().get().get();
            val expectedFileMessage = "File uploaded to ./" +
                    validatable.getUploadImageRequestRepresentation().getFile().getFilename()
                    + ", " + validatable.getUploadImageRequestRepresentation().getFile().getFile().length() + " bytes";

            assertAll("validation for uploading image",

                    () -> assertThat(actual.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value()),
                    () -> assertThat(actual.getBody().getCode()).isEqualTo(HttpStatus.OK.value()),
                    () -> assertThat(actual.getBody().getMessage()).contains(validatable.getUploadImageRequestRepresentation().getAdditionalMetadata()),
                    () -> assertThat(actual.getBody().getMessage()).contains(expectedFileMessage));
        });
    }
}
