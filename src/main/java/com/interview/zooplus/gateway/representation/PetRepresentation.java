package com.interview.zooplus.gateway.representation;

import lombok.Builder;
import lombok.Value;

import java.util.List;


@Builder(toBuilder = true)
@Value
public class PetRepresentation {

    Long id;

    String name;

    String status;

    NameIdPair category;

    List<String> photoUrls;

    List<NameIdPair> tags;


    @Value
    @Builder(toBuilder = true)
    public static class NameIdPair {

        Long id;

        String name;
    }
}
