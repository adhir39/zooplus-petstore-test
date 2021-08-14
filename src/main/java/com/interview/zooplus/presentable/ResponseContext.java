package com.interview.zooplus.presentable;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static io.vavr.API.Option;

@Value
@Slf4j
public class ResponseContext {

    private final Map<String,Object> responseContextMap;

    public ResponseContext(Map<String,Object> responseContextMap){
        this.responseContextMap = ImmutableMap.copyOf(responseContextMap);
    }


    public <T> Option<T> get(String key, Class<T> clazz){

      return Option(responseContextMap.get(key))
                .filter(clazz::isInstance)
                .map(clazz::cast);
    }

    public <T> Option<T> get(String key, TypeReference<T> typeReference){

        ObjectMapper objectMapper = new ObjectMapper();
        return Try.of(() -> responseContextMap.get(key))
                .mapTry(str -> objectMapper.convertValue(str,typeReference))
                .onFailure(Throwable::printStackTrace)
                .toOption();
    }

}
