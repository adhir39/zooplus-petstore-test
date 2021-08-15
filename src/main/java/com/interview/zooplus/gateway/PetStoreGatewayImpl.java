package com.interview.zooplus.gateway;

import com.interview.zooplus.config.PetStoreProp;
import com.interview.zooplus.core.gateway.PetStoreGateway;
import com.interview.zooplus.gateway.representation.PetRepresentation;
import com.interview.zooplus.gateway.representation.PetStoreGenericResponse;
import com.interview.zooplus.gateway.representation.UpdatePetFormDataRequestRepresentation;
import com.interview.zooplus.gateway.representation.UploadImageRequestRepresentation;
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

import static io.vavr.API.Try;

@Slf4j
@Component
public class PetStoreGatewayImpl implements PetStoreGateway {

    private final RestTemplate restTemplate;

    private final ExceptionToFailureMessage exceptionToFailureMessage;

    private static final String CREATE_PET_PATH = "/pet";

    private static final String UPLOAD_PET_IMAGE_PATH = CREATE_PET_PATH + "/{petId}/uploadImage";

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
    public Try<ResponseEntity<PetStoreGenericResponse>> uploadImageByPetId(Long petId,
                                                                           UploadImageRequestRepresentation request) {
        val headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> payLoad = new LinkedMultiValueMap<>();
        payLoad.add("additionalMetadata", request.getAdditionalMetadata());
        payLoad.add("file", request.getFile());

        val entity = new HttpEntity(payLoad, headers);
        log.info("Uploading image for pet id : {}", petId);

        return Try(() -> entity)
                .onSuccess(req -> log.info("Upload file request body : {}", req.getBody()))
                .map(requestEntity -> restTemplate.exchange(UPLOAD_PET_IMAGE_PATH, HttpMethod.POST, requestEntity, PetStoreGenericResponse.class, petId))
                .onSuccess(rEntity -> log.info("Successfully uploaded image with response code :{} and body :{}", rEntity.getStatusCodeValue(), rEntity.getBody()))
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
    public Try<ResponseEntity<PetRepresentation>> updatePet(PetRepresentation request) {
        log.info("Update pet request body : {}", request);
        return Try(() -> restTemplate.exchange(CREATE_PET_PATH, HttpMethod.PUT, new HttpEntity(request), PetRepresentation.class))
                .onSuccess(entity -> log.info("Successfully updated pet with response : {} and body :{}", entity.getStatusCodeValue(), entity.getBody()))
                .onFailure(exceptionToFailureMessage);
    }

    @Override
    public Try<ResponseEntity<List<PetRepresentation>>> findPetByStatus(List<String> status) {

        log.info("Requesting pets by group by status : {}", status);
        val uri = UriComponentsBuilder.fromPath(FIND_BY_STATUS_PATH);
        for (String stat : status)
            uri.queryParam("status", stat);
        System.out.println(uri.toUriString());
        return Try(() -> restTemplate.exchange(uri.build().toString(), HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<PetRepresentation>>() {
        }))
                .onSuccess(petList -> log.info("Successfully received pets with response code : {} and body : {}", petList.getStatusCodeValue(), petList.getBody()))
                .onFailure(exceptionToFailureMessage);
    }

    @Override
    public Try<ResponseEntity<PetRepresentation>> findPetById(Long petId) {
        log.info("Requesting Pet for id : {}", petId);
        return Try(() -> restTemplate.exchange(BY_ID_PATH, HttpMethod.GET, HttpEntity.EMPTY, PetRepresentation.class, petId))
                .onSuccess(entity -> log.info("Successfully received pet details with response code :{}, and body :{}", entity.getStatusCodeValue(), entity.getBody()))
                .onFailure(exceptionToFailureMessage);
    }

    @Override
    public Try<ResponseEntity<PetStoreGenericResponse>> updatePetFormDataById(Long petId, UpdatePetFormDataRequestRepresentation request) {
        val headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, Object> payLoad = new LinkedMultiValueMap<>();
        payLoad.add("name", request.getName());
        payLoad.add("status", request.getStatus());

        val entity = new HttpEntity(payLoad, headers);
        log.info("Updating form data request body : {} with id : {}", request, petId);

        return Try(() -> entity)
                .map(requestEntity -> restTemplate.exchange(BY_ID_PATH, HttpMethod.POST, requestEntity, PetStoreGenericResponse.class, petId))
                .onSuccess(rEntity -> log.info("Successfully updated form data with response code {} and body : {}", rEntity.getStatusCodeValue(), rEntity.getBody()))
                .onFailure(exceptionToFailureMessage);
    }

    @Override
    public Try<ResponseEntity<PetStoreGenericResponse>> deletePetById(Long petId) {

        val header = new HttpHeaders();
        header.add("api_key", "special-key");

        log.info("Requesting delete pet for id : {}", petId);
        return Try(() -> restTemplate.exchange(BY_ID_PATH, HttpMethod.DELETE, new HttpEntity(header), PetStoreGenericResponse.class, petId))
                .onFailure(exceptionToFailureMessage)
                .onSuccess(entity -> log.info("Successfully deleted pet with response code :{} and body : {}", entity.getStatusCodeValue(), entity.getBody()));
    }
}
