package com.interview.zooplus.positiveTest;

import com.interview.zooplus.TestBase;
import com.interview.zooplus.presentable.Animal;
import com.interview.zooplus.presentable.PresentableDataContainer;
import io.vavr.control.Either;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.interview.zooplus.utility.PetStoreUtility.getImageData;
import static com.interview.zooplus.utility.PetStoreUtility.getPetData;
import static io.vavr.API.Try;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UploadPetImagePositiveTest extends TestBase {

    @Test
    public void addImage_whenCatJpegImageSent_thenUploadedSuccessfully() {
        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.cat, null).toBuilder()
                        .photoUrls(null)
                        .build())
                .uploadImageRequestRepresentation(getImageData("cat845683.jpeg", 50000))
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(uploadImageByIdUseCase).map(Either::get)
                .map(container -> container.addValidator(uploadImageValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void addImage_whenCatPngImageSent_thenUploadedSuccessfully() {
        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.cat, null).toBuilder()
                        .photoUrls(null)
                        .build())
                .uploadImageRequestRepresentation(getImageData("cat_nice.png", 50000))
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(uploadImageByIdUseCase).map(Either::get)
                .map(container -> container.addValidator(uploadImageValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void addImage_whenCatJpegWithMaxSizeSent_thenUploadedSuccessfully() {
        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.cat, null).toBuilder()
                        .photoUrls(null)
                        .build())
                .uploadImageRequestRepresentation(getImageData("cat_beautiful.jpeg", 100000))
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(uploadImageByIdUseCase).map(Either::get)
                .map(container -> container.addValidator(uploadImageValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }

    @Test
    public void addImage_whenCatPngWithMaxSizeSent_thenUploadedSuccessfully() {
        Try(() -> PresentableDataContainer.builder()
                .petRepresentation(getPetData(Animal.cat, null).toBuilder()
                        .photoUrls(null)
                        .build())
                .uploadImageRequestRepresentation(getImageData("cat_beautiful_fat.png", 100000))
                .build())
                .map(addPetUseCase).map(Either::get)
                .map(uploadImageByIdUseCase).map(Either::get)
                .map(container -> container.addValidator(uploadImageValidator.validate(container)))
                .onSuccess(PresentableDataContainer::validateAll)
                .onFailure(Assertions::fail);
    }
}
