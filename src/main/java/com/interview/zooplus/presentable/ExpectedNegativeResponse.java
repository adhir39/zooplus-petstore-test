package com.interview.zooplus.presentable;

import lombok.Builder;
import lombok.Value;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Value
@Builder(toBuilder = true)
public class ExpectedNegativeResponse {

    HttpStatus expectedHttpStatus;

    String expectedErrorMessage;

    Map<String, Object> expectedHeader;
}
