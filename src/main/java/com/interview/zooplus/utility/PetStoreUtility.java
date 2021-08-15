package com.interview.zooplus.utility;

import com.interview.zooplus.gateway.representation.PetRepresentation;
import com.interview.zooplus.gateway.representation.PetRepresentation.NameIdPair;
import com.interview.zooplus.gateway.representation.UploadImageRequestRepresentation;
import com.interview.zooplus.presentable.Animal;
import com.interview.zooplus.presentable.PetStatus;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.val;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

@UtilityClass
public class PetStoreUtility {


    public static PetRepresentation getPetData(Animal animal, Long petId) {

        val id = Objects.nonNull(petId) ? petId : null;
        return PetRepresentation.builder()
                .id(id)
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
        val tagSize = new Random().nextInt(tags.size() - 1) + 1;

      return  tags.stream()
              .limit(tagSize)
              .map(tagName -> NameIdPair.builder()
                      .id(getRandomId())
                      .name(tagName)
                      .build())
              .collect(Collectors.toList());
    }

    public String getRandomValueFromList(List<String> vals) {
        return vals.get(new Random().nextInt(vals.size()));
    }

    public long getRandomId() {
        return new SecureRandom().nextInt(50) + 1;
    }

    public String getAlternateStatus(String status) {
        val petStatus = PetStatus.values();
        Random rand = new Random();
        String alternateStatus = petStatus[rand.nextInt(petStatus.length)].name();
        while (alternateStatus.equals(status) || alternateStatus.equals(PetStatus.invalid.name()))
            alternateStatus = petStatus[rand.nextInt(petStatus.length)].name();
        return alternateStatus;
    }

    @SneakyThrows
    public UploadImageRequestRepresentation getImageData(String fileName, long fileSize) {

        return UploadImageRequestRepresentation.builder()
                .file(createFile(fileName, fileSize))
                .additionalMetadata("any random meta data")
                .build();
    }

    public Resource createFile(String fileName, long fileSize) throws IOException {
        File file = new File(fileName);
        file.createNewFile();

        RandomAccessFile rFile = new RandomAccessFile(file, "rw");
        rFile.setLength(fileSize);
        rFile.close();

        return new FileSystemResource(file);
    }
}
