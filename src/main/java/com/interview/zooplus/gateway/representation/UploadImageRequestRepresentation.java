package com.interview.zooplus.gateway.representation;

import lombok.Builder;
import lombok.Value;

import java.io.File;

@Value
@Builder(toBuilder = true)
public class UploadImageRequestRepresentation {

    String additionalMetadata;

    File file;
}


