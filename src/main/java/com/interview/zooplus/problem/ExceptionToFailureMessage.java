package com.interview.zooplus.problem;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@Slf4j
@RequiredArgsConstructor
public class ExceptionToFailureMessage implements Consumer<Throwable> {

    private final ExceptionToProblemMapper exceptionToProblemMapper;

    @Override
    public void accept(Throwable throwable) {
        PetStoreProblem petStoreProblem = exceptionToProblemMapper.apply(throwable);
        log.info("Response : {} ", petStoreProblem);
    }
}
