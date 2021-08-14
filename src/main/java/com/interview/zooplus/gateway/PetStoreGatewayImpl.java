package com.interview.zooplus.gateway;

import com.fasterxml.jackson.databind.JsonNode;
import com.interview.zooplus.config.PetStoreProp;
import com.interview.zooplus.core.gateway.PetStoreGateway;
import com.interview.zooplus.gateway.representation.PetRepresentation;
import com.interview.zooplus.gateway.representation.UpdatePetRequestRepresentation;
import com.interview.zooplus.gateway.representation.UploadImageRequestRepresentation;
import com.interview.zooplus.gateway.representation.UploadImageResponseRepresentation;

import static io.vavr.API.Try;

import com.interview.zooplus.presentable.PetStatus;
import com.interview.zooplus.problem.ExceptionToFailureMessage;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Slf4j
@Component
public class PetStoreGatewayImpl implements PetStoreGateway {

    private final RestTemplate restTemplate;

    private final ExceptionToFailureMessage exceptionToFailureMessage;

    private static final String CREATE_PET_PATH = "/pet";

    private static final String UPLOAD_PET_IMAGE_PATH = CREATE_PET_PATH + "/petId}/uploadImage";

    private static final String FIND_BY_STATUS_PATH = CREATE_PET_PATH + "/findByStatus";

    private static final String BY_ID_PATH = CREATE_PET_PATH + "/{petId}";

    public PetStoreGatewayImpl(RestTemplateBuilder restTemplateBuilder,
                               PetStoreProp petStoreProp,
                               ExceptionToFailureMessage exceptionToFailureMessage) {

        this.restTemplate = restTemplateBuilder.rootUri(petStoreProp.getUrl().toString())
                .defaultHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
                .build();
        this.exceptionToFailureMessage = exceptionToFailureMessage;
    }

    @Override
    public Try<UploadImageResponseRepresentation> uploadImageByPetId(Long petId,
                                                                     UploadImageRequestRepresentation request) {
        val headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> payLoad = new LinkedMultiValueMap<>();
        payLoad.add("additionalMetadata", request.getAdditionalMetadata());
        payLoad.add("file", request.getFile());

        val entity = new HttpEntity(payLoad, headers);
        log.info("Uploading image for pet id : {}", petId);

        return Try(() -> entity)
                .map(requestEntity -> restTemplate.exchange(UPLOAD_PET_IMAGE_PATH, HttpMethod.POST, requestEntity, UploadImageResponseRepresentation.class, petId))
                .map(ResponseEntity::getBody)
                .onSuccess(res -> log.info("Successfully uploaded image for pet id :{}", petId))
                .onFailure(exceptionToFailureMessage);
    }

    @Override
    public Try<ResponseEntity<PetRepresentation>> addPet(PetRepresentation request) {
        log.info("Create pet request body : {}", request);
        return Try(() -> restTemplate.exchange(CREATE_PET_PATH, HttpMethod.POST, new HttpEntity(request), PetRepresentation.class))
                .onSuccess(responseEntity -> log.info("Successfully added pet with response code: {} and response : {}", responseEntity.getStatusCodeValue(),responseEntity.getBody()))
                .onFailure(Throwable::printStackTrace)
                .onFailure(exceptionToFailureMessage);
    }

    @Override
    public Try<JsonNode> updatePet(PetRepresentation request) {
        log.info("Update pet request body : {}", request);
        return Try(() -> restTemplate.exchange(CREATE_PET_PATH, HttpMethod.PUT, new HttpEntity(request), JsonNode.class))
                .map(ResponseEntity::getBody)
                .onSuccess(node -> log.info("Successfully added pet with response : {}", node))
                .onFailure(exceptionToFailureMessage);
    }

    @Override
    public Try<List<PetRepresentation>> findPetByStatus(List<PetStatus> status) {

        log.info("Requesting pets by group by status : {}", status);
        val uri = UriComponentsBuilder.fromPath(FIND_BY_STATUS_PATH)
                .query("status={val}").build(status.toString());

        return Try(() -> restTemplate.exchange(uri.toString(), HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<PetRepresentation>>() {
        }))
                .map(ResponseEntity::getBody)
                .onSuccess(petList -> log.info("Successfully received pets by status, response : {}", petList))
                .onFailure(exceptionToFailureMessage);
    }

    @Override
    public Try<PetRepresentation> findPetById(Long petId) {
        log.info("Requesting Pet for id : {}", petId);
        return Try(() -> restTemplate.getForObject(BY_ID_PATH, PetRepresentation.class, petId))
                .onSuccess(res -> log.info("Successfully received pet details for id : {}, response : {}", petId, res))
                .onFailure(exceptionToFailureMessage);
    }

    @Override
    public Try<PetRepresentation> updatePetById(Long petId, UpdatePetRequestRepresentation request) {
        val headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> payLoad = new LinkedMultiValueMap<>();
        payLoad.add("name", request.getName());
        payLoad.add("status", request.getStatus());

        val entity = new HttpEntity(payLoad, headers);
        log.info("Updating pet request body : {} with id : {}", request, petId);

        return Try(() -> entity)
                .map(requestEntity -> restTemplate.exchange(BY_ID_PATH, HttpMethod.POST, requestEntity, PetRepresentation.class, petId))
                .map(ResponseEntity::getBody)
                .onSuccess(res -> log.info("Successfully updated pet with id :{}, response : {}", petId, res))
                .onFailure(exceptionToFailureMessage);
    }

    @Override
    public Try<Void> deletePetById(Long petId) {

        val header = new HttpHeaders();
        header.add("api_key", "special-key");

        log.info("Requesting deelte pet for id : {}",petId);
        return Try(() -> restTemplate.exchange(BY_ID_PATH, HttpMethod.DELETE, new HttpEntity(header), Void.class, petId))
                .map(ResponseEntity::getBody)
                .onFailure(exceptionToFailureMessage)
                .onSuccess($_ -> log.info("Successfully deleted pet with id : {}", petId));
    }
}
