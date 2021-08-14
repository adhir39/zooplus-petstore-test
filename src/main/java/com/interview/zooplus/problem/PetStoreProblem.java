package com.interview.zooplus.problem;

import com.github.bduisenov.cca.problem.Problem;
import lombok.Value;
import org.springframework.http.HttpHeaders;

@Value
public class PetStoreProblem implements Problem {

    int statusCode;

    String statusLine;

    String responseBody;

    HttpHeaders responseHeaders;

    String errorMessage;

    @Override
    public String getMessage() {
        return errorMessage;
    }

    @Override
    public Enum<?> getSource() {
        return null;
    }
}
