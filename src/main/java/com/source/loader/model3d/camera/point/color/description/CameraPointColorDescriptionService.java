package com.source.loader.model3d.camera.point.color.description;

import com.source.loader.technical.unique.string.customizer.UniqueStringCustomizer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CameraPointColorDescriptionService {
    private final CameraPointColorDescriptionRepository cameraPointColorDescriptionRepository;

    public CameraPointColorDescription createCameraColorDescription(String name){
        CameraPointColorDescription cameraPointColorDescription = cameraPointColorDescriptionRepository.findByName(UniqueStringCustomizer.capitalizeRecord(name)).orElse(null);
        if(cameraPointColorDescription == null){
            cameraPointColorDescription = CameraPointColorDescription.builder()
                    .name(UniqueStringCustomizer.capitalizeRecord(name))
                    .build();
            cameraPointColorDescription = cameraPointColorDescriptionRepository.save(cameraPointColorDescription);
        }
        return cameraPointColorDescription;
    }
}
