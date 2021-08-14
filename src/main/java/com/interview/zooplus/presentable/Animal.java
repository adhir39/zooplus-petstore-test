package com.interview.zooplus.presentable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;

@RequiredArgsConstructor
@Getter
public enum Animal {

    cat(asList("category 1", "category 2", "category 3"),
            asList("tag 1", "tag 2", "tag 3", "tag 4"),
            asList("https://pixabay.com/photos/cat-young-animal-kitten-gray-cat-2083492/",
                    "https://pixabay.com/photos/cat-kitten-pet-kitty-young-cat-551554/",
                    "https://pixabay.com/photos/cat-flower-kitten-stone-pet-2536662/",
                    "https://pixabay.com/photos/cat-kitten-tree-curious-tabby-1647775/",
                    "https://pixabay.com/photos/cat-pet-yawning-yawn-animal-339400/")),

    dog(asList("category 10", "category 20", "category 30"),
            asList("tag 10", "tag 20", "tag 30", "tag 40"),
            asList("https://www.pexels.com/photo/closeup-photography-of-adult-short-coated-tan-and-white-dog-sleeping-on-gray-textile-at-daytime-731022/",
                    "https://www.pexels.com/photo/dog-snout-puppy-royalty-free-97082/",
                    "https://www.pexels.com/photo/brown-and-white-short-coated-puppy-1805164/",
                    "https://www.pexels.com/photo/short-coated-tan-dog-2253275/",
                    "https://www.pexels.com/photo/two-yellow-labrador-retriever-puppies-1108099/")),

    horse(asList("category 11", "category 12", "category 13"),
            asList("tag 11", "tsag 12", "tag 13", "tag 14"),
            asList("https://www.pexels.com/photo/white-horse-on-green-grass-1996333/",
                    "https://www.pexels.com/photo/brown-horse-on-grass-field-635499/",
                    "https://www.pexels.com/photo/brown-horse-beside-gray-metal-bar-53114/",
                    "https://www.pexels.com/photo/animal-close-up-country-countryside-459124/",
                    "https://www.pexels.com/photo/agriculture-animal-close-up-countryside-461717/"));

    private final List<String> categoryNames;

    private final List<String> tagNames;

    private final List<String> photoUrls;


    public static Animal getRandomAnimal() {
        Animal[] animals = Animal.values();
        return animals[new Random().nextInt(animals.length)];
    }
}
