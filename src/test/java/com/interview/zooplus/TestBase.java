package com.interview.zooplus;

import com.github.bduisenov.cca.UseCase;
import com.github.bduisenov.cca.problem.Problem;
import com.interview.zooplus.presentable.PresentableDataContainer;
import com.interview.zooplus.validator.Validation;
import io.vavr.control.Either;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public abstract class TestBase {

    @Autowired
    protected UseCase<PresentableDataContainer, Either<Problem, PresentableDataContainer>> addPetUseCase;

    @Autowired
    protected UseCase<PresentableDataContainer, Either<Problem, PresentableDataContainer>> updatePetUseCase;

    @Autowired
    protected UseCase<PresentableDataContainer, Either<Problem, PresentableDataContainer>> findPetByIdUseCase;

    @Autowired
    protected UseCase<PresentableDataContainer, Either<Problem, PresentableDataContainer>> updatePetFormDataByIdUseCase;

    @Autowired
    protected UseCase<PresentableDataContainer, Either<Problem, PresentableDataContainer>> deletePetByIdUseCase;

    @Autowired
    protected UseCase<PresentableDataContainer, Either<Problem, PresentableDataContainer>> uploadImageByIdUseCase;

    @Autowired
    protected UseCase<PresentableDataContainer, Either<Problem, PresentableDataContainer>> findPetByStatusUseCase;

    @Autowired
    protected Validation<PresentableDataContainer> addPetValidator;

    @Autowired
    protected Validation<PresentableDataContainer> updatePetValidator;

    @Autowired
    protected Validation<PresentableDataContainer> findPetByIdValidator;

    @Autowired
    protected Validation<PresentableDataContainer> updatedFormDataValidator;

    @Autowired
    protected Validation<PresentableDataContainer> deletePetValidator;

    @Autowired
    protected Validation<PresentableDataContainer> uploadImageValidator;

    @Autowired
    protected Validation<PresentableDataContainer> findByStatusValidator;

    @BeforeEach
    public void displayTestName(TestInfo testInfo) {
        log.info("Test method name : {}", testInfo.getDisplayName());
    }

    @AfterEach
    public void tearDown() {
        log.info("..............................................END OF TEST............................................");
    }
}
