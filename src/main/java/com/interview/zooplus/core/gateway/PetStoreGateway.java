package com.interview.zooplus.core.gateway;

import com.interview.zooplus.gateway.representation.PetRepresentation;
import com.interview.zooplus.gateway.representation.PetStoreGenericResponse;
import com.interview.zooplus.gateway.representation.UpdatePetFormDataRequestRepresentation;
import com.interview.zooplus.gateway.representation.UploadImageRequestRepresentation;
import io.vavr.control.Try;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PetStoreGateway {

    Try<ResponseEntity<PetStoreGenericResponse>> uploadImageByPetId(Long petId, UploadImageRequestRepresentation request);

    Try<ResponseEntity<PetRepresentation>> addPet(PetRepresentation request);

    Try<ResponseEntity<PetRepresentation>> updatePet(PetRepresentation request);

    Try<ResponseEntity<List<PetRepresentation>>> findPetByStatus(List<String> status);

    Try<ResponseEntity<PetRepresentation>> findPetById(Long petId);

    Try<ResponseEntity<PetStoreGenericResponse>> updatePetFormDataById(Long petId, UpdatePetFormDataRequestRepresentation request);

    Try<ResponseEntity<PetStoreGenericResponse>> deletePetById(Long petId);
}
