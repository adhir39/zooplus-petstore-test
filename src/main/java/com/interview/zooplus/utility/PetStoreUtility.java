package com.interview.zooplus.utility;

import com.interview.zooplus.gateway.representation.PetRepresentation;
import com.interview.zooplus.gateway.representation.PetRepresentation.NameIdPair;
import com.interview.zooplus.presentable.Animal;
import com.interview.zooplus.presentable.PetStatus;
import lombok.experimental.UtilityClass;
import lombok.val;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@UtilityClass
public class PetStoreUtility {


    public PetRepresentation getPetData(Animal animal){

        return PetRepresentation.builder()
                .category(NameIdPair.builder()
                        .id(getRandomId())
                        .name(getRandomValueFromList(animal.getCategoryNames()))
                        .build())
                .photoUrls(animal.getPhotoUrls())
                .status(PetStatus.available.name())
                .tags(getTags(animal.getTagNames()))
                .name(animal.name())
                .build();
    }

    public List<NameIdPair> getTags(List<String> tags){
        Collections.shuffle(tags);
      val tagSize = new Random().nextInt(tags.size());

      return  tags.stream()
              .limit(tagSize)
              .map(tagName -> NameIdPair.builder()
                      .id(getRandomId())
                      .name(tagName)
                      .build())
              .collect(Collectors.toList());
    }

    private String getRandomValueFromList(List<String> vals){
        return vals.get(new Random().nextInt(vals.size()));
    }
    private long getRandomId(){
        return new SecureRandom().nextInt(50) + 1;
    }
}
