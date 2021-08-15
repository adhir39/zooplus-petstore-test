package com.interview.zooplus.negativeTest;

import com.interview.zooplus.TestBase;
import com.interview.zooplus.presentable.Animal;
import com.interview.zooplus.presentable.ExpectedNegativeResponse;
import com.interview.zooplus.presentable.PresentableDataContainer;
import io.vavr.control.Either;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static com.interview.zooplus.utility.PetStoreUtility.getImageData;
import static com.interview.zooplus.utility.PetStoreUtility.getPetData;
import static io.vavr.API.Try;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UploadImageNegativeTest extends TestBase {

    @Test
    public void addImage_whenCatJpegImageExceedsMaxSize_thenHttpStatus400() {
        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.cat, null).toBuilder()
                        .photoUrls(null)
                        .build())
                .expectedNegativeResponse(ExpectedNegativeResponse.builder()
                        .expectedHttpStatus(HttpStatus.BAD_REQUEST)
                        .build())
                .uploadImageRequestRepresentation(getImageData("cat_fail.jpeg", 150000))
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(uploadImageByIdUseCase).map(Either::get)
                .map(container -> container.addValidator(uploadImageValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void addImage_whenCatPngImageExceedsMaxSize_thenHttpStatus400() {
        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.cat, null).toBuilder()
                        .photoUrls(null)
                        .build())
                .expectedNegativeResponse(ExpectedNegativeResponse.builder()
                        .expectedHttpStatus(HttpStatus.BAD_REQUEST)
                        .build())
                .uploadImageRequestRepresentation(getImageData("cat_fail.png", 150000))
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(uploadImageByIdUseCase).map(Either::get)
                .map(container -> container.addValidator(uploadImageValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void addImage_whenPetIdRandom_thenHttpStatus404() {
        Try(() -> PresentableDataContainer.builder()
                .expectedNegativeResponse(ExpectedNegativeResponse.builder()
                        .expectedHttpStatus(HttpStatus.NOT_FOUND)
                        .build())
                .uploadImageRequestRepresentation(getImageData("cat_ggtjk.png", 40000))
                .build().putPetId(345643l))
                .map(uploadImageByIdUseCase).map(Either::get)
                .map(container -> container.addValidator(uploadImageValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }
}
