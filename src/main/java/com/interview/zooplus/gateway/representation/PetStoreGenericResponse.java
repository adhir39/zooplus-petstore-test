package com.interview.zooplus.gateway.representation;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class PetStoreGenericResponse {

    int code;

    String type;

    String message;
}
