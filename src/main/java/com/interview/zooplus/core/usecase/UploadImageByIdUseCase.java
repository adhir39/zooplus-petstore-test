package com.interview.zooplus.core.usecase;

import com.github.bduisenov.cca.UseCase;
import com.github.bduisenov.cca.problem.Problem;
import com.interview.zooplus.core.gateway.PetStoreGateway;
import com.interview.zooplus.presentable.PresentableDataContainer;
import com.interview.zooplus.problem.ExceptionToProblemMapper;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static io.vavr.API.For;
import static io.vavr.API.Option;

@Component
@RequiredArgsConstructor
public class UploadImageByIdUseCase implements UseCase<PresentableDataContainer, Either<Problem, PresentableDataContainer>> {

    private final PetStoreGateway petStoreGateway;

    private final ExceptionToProblemMapper exceptionToProblemMapper;


    @Override
    public Either<Problem, PresentableDataContainer> execute(PresentableDataContainer presentableDataContainer) {
        return For(presentableDataContainer.getPetId(),
                Option(presentableDataContainer.getUploadImageRequestRepresentation()))
                .yield((idOpt, dataOpt) -> petStoreGateway.uploadImageByPetId(idOpt, dataOpt)
                        .toEither().mapLeft(exceptionToProblemMapper))
                .map(presentableDataContainer::putUploadImageResponse)
                .toTry()
                .toEither()
                .mapLeft(exceptionToProblemMapper);
    }
}
