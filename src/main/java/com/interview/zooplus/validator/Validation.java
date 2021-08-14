package com.interview.zooplus.validator;

import org.junit.jupiter.api.function.Executable;

import java.util.stream.Stream;

public interface Validation<T> {
    Stream<Executable> validate(T validatable);
}
