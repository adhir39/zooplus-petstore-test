package com.interview.zooplus.presentable;

import com.google.common.collect.Streams;
import com.interview.zooplus.gateway.representation.PetRepresentation;
import com.interview.zooplus.problem.PetStoreProblem;
import io.vavr.control.Either;
import io.vavr.control.Option;
import lombok.Builder;
import lombok.Value;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;
import org.springframework.http.ResponseEntity;


import java.util.HashMap;
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

    private static final String ADD_PET_RESPONSE = "add.pet.response";

    private static final String SOFT_ASSERTIONS_KEY = "soft.assertions";

    public PresentableDataContainer putAddPetResponse(Either<PetStoreProblem, ResponseEntity<PetRepresentation>> response){
        return addOrReplaceContextAttribute(ADD_PET_RESPONSE, response);
    }

    public Option<Either<PetStoreProblem, ResponseEntity<PetRepresentation>>> getAddPetResponse(){
        return (Option<Either<PetStoreProblem, ResponseEntity<PetRepresentation>>>)(Object) this.responseContext.get(ADD_PET_RESPONSE, Either.class);
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
