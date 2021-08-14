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
    protected Validation<PresentableDataContainer> addPetValidator;

    @BeforeEach
    public void displayTestName(TestInfo testInfo){
        log.info("Test method name : {}",testInfo.getDisplayName());
    }

    @AfterEach
    public void tearDown(){
        log.info(".............................END OF TEST........................");
    }
}
