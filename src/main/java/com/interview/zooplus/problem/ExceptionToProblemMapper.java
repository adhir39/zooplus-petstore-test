package com.interview.zooplus.problem;

import io.vavr.Predicates;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;

import java.io.IOException;
import java.util.function.Function;

import static io.vavr.API.*;

@Component
public class ExceptionToProblemMapper implements Function<Throwable, PetStoreProblem> {


    @Override
    public PetStoreProblem apply(Throwable throwable) {

        return Match(throwable)
            .of(Case($(Predicates.instanceOf(HttpStatusCodeException.class)), th -> new PetStoreProblem(th.getStatusCode().value(),th.getStatusText(),th.getResponseBodyAsString(),th.getResponseHeaders(), null)),
                Case($(Predicates.instanceOf(IOException.class)), th -> new PetStoreProblem(0,th.toString(),th.getMessage(),null, th.getMessage())),
                Case($(Predicates.instanceOf(ResourceAccessException.class)), th -> new PetStoreProblem(0,th.toString(),th.getMessage(),null, th.getMessage())),
                Case($(),th -> new PetStoreProblem(0,th.getClass().getName(),th.getStackTrace().toString(), null,th.getMessage()))
                );

    }
}
