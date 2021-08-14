package com.interview.zooplus.core.gateway;

import com.fasterxml.jackson.databind.JsonNode;
import com.interview.zooplus.gateway.representation.PetRepresentation;
import com.interview.zooplus.gateway.representation.UpdatePetRequestRepresentation;
import com.interview.zooplus.gateway.representation.UploadImageRequestRepresentation;
import com.interview.zooplus.gateway.representation.UploadImageResponseRepresentation;
import com.interview.zooplus.presentable.PetStatus;
import io.vavr.control.Try;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PetStoreGateway {

    Try<UploadImageResponseRepresentation> uploadImageByPetId(Long petId, UploadImageRequestRepresentation request);

    Try<ResponseEntity<PetRepresentation>> addPet(PetRepresentation request);

    Try<JsonNode> updatePet(PetRepresentation request);

    Try<List<PetRepresentation>> findPetByStatus(List<PetStatus> status);

    Try<PetRepresentation> findPetById(Long petId);

    Try<PetRepresentation> updatePetById(Long petId, UpdatePetRequestRepresentation request);

    Try<Void> deletePetById(Long petId);
}
