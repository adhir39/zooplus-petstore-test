package com.interview.zooplus.gateway.representation;

import lombok.Builder;
import lombok.Value;
import org.springframework.core.io.Resource;

@Value
@Builder(toBuilder = true)
public class UploadImageRequestRepresentation {

    String additionalMetadata;

    Resource file;
}


