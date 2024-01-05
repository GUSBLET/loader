package com.source.loader.model3d.camera.point.position.updating;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CameraPointPositionUpdatingService {
    private static final List<CameraPointPositionUpdating> list = new ArrayList<>();

    public String addSecretKey(String secretKey){
        list.add(CameraPointPositionUpdating.builder()
                .timestamp(LocalTime.now())
                .secretKey(secretKey)
                .build());
        return secretKey;
    }

    public boolean findSecretKey(String secretKey){
        for (CameraPointPositionUpdating record : list) {
            if (record.getSecretKey().equals(secretKey)) {
                list.remove(record);
                return true;
            }
        }
        return false;
    }


    @Scheduled(fixedRate = 300000)
    public void checkAndRemoveExpiredRecords() {
        System.out.println("Checking and removing expired records...");

        LocalTime currentTime = LocalTime.now();
        Iterator<CameraPointPositionUpdating> iterator = list.iterator();

        while (iterator.hasNext()) {
            CameraPointPositionUpdating record = iterator.next();
            LocalTime recordTime = record.getTimestamp();
            long minutesElapsed = recordTime.until(currentTime, java.time.temporal.ChronoUnit.MINUTES);

            if (minutesElapsed >= 5) {
                System.out.println("Removing record with secret key: " + record.getSecretKey());
                iterator.remove();
            }
        }
    }
}
