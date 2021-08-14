package com.interview.zooplus.core.usecase;

import com.github.bduisenov.cca.UseCase;
import com.github.bduisenov.cca.problem.Problem;
import com.interview.zooplus.core.gateway.PetStoreGateway;
import com.interview.zooplus.presentable.PresentableDataContainer;
import com.interview.zooplus.problem.ExceptionToProblemMapper;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static io.vavr.API.Try;

@Component
@RequiredArgsConstructor
public class AddPetUseCase implements UseCase<PresentableDataContainer, Either<Problem, PresentableDataContainer>> {

    private final PetStoreGateway petStoreGateway;

    private final ExceptionToProblemMapper exceptionToProblemMapper;

    @Override
    public Either<Problem, PresentableDataContainer> execute(PresentableDataContainer presentableDataContainer) {
        return Try(() -> presentableDataContainer.getPetRepresentation())
                .map(request -> petStoreGateway.addPet(request)
                        .toEither()
                        .mapLeft(exceptionToProblemMapper))
                .map(presentableDataContainer::putAddPetResponse)
                .toEither()
                .mapLeft(exceptionToProblemMapper);
    }
}
