package com.interview.zooplus.presentable;

import java.util.Random;

public enum PetStatus {

    available,

    pending,

    sold,

    invalid;


    public static PetStatus getRandomStatus(){
        PetStatus[] status = PetStatus.values();
        Random rand = new Random();
        PetStatus randomStatus = status[rand.nextInt(status.length)];
        while(randomStatus == PetStatus.invalid)
            randomStatus = status[rand.nextInt(status.length)];
        return randomStatus;
    }
}
