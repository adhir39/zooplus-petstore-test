package com.interview.zooplus.presentable;

import com.google.common.collect.Streams;
import com.interview.zooplus.gateway.representation.PetRepresentation;
import com.interview.zooplus.gateway.representation.PetStoreGenericResponse;
import com.interview.zooplus.gateway.representation.UpdatePetFormDataRequestRepresentation;
import com.interview.zooplus.gateway.representation.UploadImageRequestRepresentation;
import com.interview.zooplus.problem.PetStoreProblem;
import io.vavr.control.Either;
import io.vavr.control.Option;
import lombok.Builder;
import lombok.Value;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Collections.emptyMap;

@Value
@Builder(toBuilder = true)
public class PresentableDataContainer {

    PetRepresentation petRepresentation;

    @Builder.Default
    ResponseContext responseContext = new ResponseContext(emptyMap());

    ExpectedNegativeResponse expectedNegativeResponse;

    UpdatePetFormDataRequestRepresentation updatePetFormDataRequestRepresentation;

    UploadImageRequestRepresentation uploadImageRequestRepresentation;

    List<String> petStatusToSearchFor;

    List<PetRepresentation> petList;

    private static final String ADD_PET_RESPONSE = "add.pet.response";

    private static final String UPDATE_PET_RESPONSE = "update.pet.response";

    private static final String GET_PET_RESPONSE = "get.pet.response";

    private static final String ADD_PET_ID = "add.pet.id";

    private static final String UPDATE_FORM_DATA_RESPONSE = "update.form.data.response";

    private static final String DELETE_PET_ID_RESPONSE = "delete.pet.id.response";

    private static final String UPLOAD_IMAGE_RESPONSE = "upload.pet.image.response";

    private static final String FIND_BY_STATUS_RESPONSE = "find.by.status.response";

    private static final String SOFT_ASSERTIONS_KEY = "soft.assertions";

    public PresentableDataContainer putAddPetResponse(Either<PetStoreProblem, ResponseEntity<PetRepresentation>> response) {
        return addOrReplaceContextAttribute(ADD_PET_RESPONSE, response);
    }

    public Option<Either<PetStoreProblem, ResponseEntity<PetRepresentation>>> getAddPetResponse() {
        return (Option<Either<PetStoreProblem, ResponseEntity<PetRepresentation>>>) (Object) this.responseContext.get(ADD_PET_RESPONSE, Either.class);
    }

    public PresentableDataContainer putUpdatePetResponse(Either<PetStoreProblem, ResponseEntity<PetRepresentation>> response) {
        return addOrReplaceContextAttribute(UPDATE_PET_RESPONSE, response);
    }

    public Option<Either<PetStoreProblem, ResponseEntity<PetRepresentation>>> getUpdatedPetResponse() {
        return (Option<Either<PetStoreProblem, ResponseEntity<PetRepresentation>>>) (Object) this.responseContext.get(UPDATE_PET_RESPONSE, Either.class);
    }

    public PresentableDataContainer putGetPetResponse(Either<PetStoreProblem, ResponseEntity<PetRepresentation>> response) {
        return addOrReplaceContextAttribute(GET_PET_RESPONSE, response);
    }

    public Option<Either<PetStoreProblem, ResponseEntity<PetRepresentation>>> getPetResponse() {
        return (Option<Either<PetStoreProblem, ResponseEntity<PetRepresentation>>>) (Object) this.responseContext.get(GET_PET_RESPONSE, Either.class);
    }

    public PresentableDataContainer putUpdateFormDataResponse(Either<PetStoreProblem, ResponseEntity<PetStoreGenericResponse>> response) {
        return addOrReplaceContextAttribute(UPDATE_FORM_DATA_RESPONSE, response);
    }

    public Option<Either<PetStoreProblem, ResponseEntity<PetStoreGenericResponse>>> getUpdatedFormDataResponse() {
        return (Option<Either<PetStoreProblem, ResponseEntity<PetStoreGenericResponse>>>) (Object) this.responseContext.get(UPDATE_FORM_DATA_RESPONSE, Either.class);
    }

    public PresentableDataContainer putDeleteResponse(Either<PetStoreProblem, ResponseEntity<PetStoreGenericResponse>> response) {
        return addOrReplaceContextAttribute(DELETE_PET_ID_RESPONSE, response);
    }

    public Option<Either<PetStoreProblem, ResponseEntity<PetStoreGenericResponse>>> getDeleteResponse() {
        return (Option<Either<PetStoreProblem, ResponseEntity<PetStoreGenericResponse>>>) (Object) this.responseContext.get(DELETE_PET_ID_RESPONSE, Either.class);
    }

    public PresentableDataContainer putUploadImageResponse(Either<PetStoreProblem, ResponseEntity<PetStoreGenericResponse>> response) {
        return addOrReplaceContextAttribute(UPLOAD_IMAGE_RESPONSE, response);
    }

    public Option<Either<PetStoreProblem, ResponseEntity<PetStoreGenericResponse>>> getUploadImageResponse() {
        return (Option<Either<PetStoreProblem, ResponseEntity<PetStoreGenericResponse>>>) (Object) this.responseContext.get(UPLOAD_IMAGE_RESPONSE, Either.class);
    }

    public PresentableDataContainer putFindByStatusResponse(Either<PetStoreProblem, ResponseEntity<List<PetRepresentation>>> response) {
        return addOrReplaceContextAttribute(FIND_BY_STATUS_RESPONSE, response);
    }

    public Option<Either<PetStoreProblem, ResponseEntity<List<PetRepresentation>>>> getFindByStatusResponse() {
        return (Option<Either<PetStoreProblem, ResponseEntity<List<PetRepresentation>>>>) (Object) this.responseContext.get(FIND_BY_STATUS_RESPONSE, Either.class);
    }

    public PresentableDataContainer putPetId(Long id) {
        return addOrReplaceContextAttribute(ADD_PET_ID, id);
    }

    public Option<Long> getPetId() {
        return this.responseContext.get(ADD_PET_ID, Long.class);
    }

    @SuppressWarnings("unchecked")
    private Option<Stream<Executable>> getSoftAssertions() {
        return (Option<Stream<Executable>>) (Object) responseContext
                .get(SOFT_ASSERTIONS_KEY, Stream.class);
    }

    public void validateAll() {
        getSoftAssertions()
                .peek(Assertions::assertAll);
    }

    public PresentableDataContainer addValidator(Stream<Executable> softAssertions) {
        Option<Stream<Executable>> currentSoftAssertions = getSoftAssertions();

        Stream<Executable> result = currentSoftAssertions
                .map(current -> Streams.concat(current, softAssertions))
                .getOrElse(softAssertions);

        return addOrReplaceContextAttribute(SOFT_ASSERTIONS_KEY, result);
    }
    private PresentableDataContainer addOrReplaceContextAttribute(String key, Object value) {

        Map<String, Object> r = new HashMap<>();
        r.putAll(this.getResponseContext().getResponseContextMap());
        r.put(key, value);
        return this.toBuilder().responseContext(new ResponseContext(r))
                .build();
    }
}
