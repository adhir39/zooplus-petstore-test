package com.interview.zooplus.core.usecase;

import com.github.bduisenov.cca.UseCase;
import com.github.bduisenov.cca.problem.Problem;
import com.interview.zooplus.core.gateway.PetStoreGateway;
import com.interview.zooplus.presentable.PresentableDataContainer;
import com.interview.zooplus.problem.ExceptionToProblemMapper;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindPetByIdUseCase implements UseCase<PresentableDataContainer, Either<Problem, PresentableDataContainer>> {

    private final PetStoreGateway petStoreGateway;

    private final ExceptionToProblemMapper exceptionToProblemMapper;

    @Override
    public Either<Problem, PresentableDataContainer> execute(PresentableDataContainer presentableDataContainer) {
        return presentableDataContainer.getPetId().toTry()
                .map(id -> petStoreGateway.findPetById(id)
                        .toEither().mapLeft(exceptionToProblemMapper))
                .map(presentableDataContainer::putGetPetResponse)
                .toEither().mapLeft(exceptionToProblemMapper);
    }
}
