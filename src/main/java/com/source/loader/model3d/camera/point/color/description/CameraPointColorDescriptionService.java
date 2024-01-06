package com.source.loader.model3d.camera.point.color.description;

import com.source.loader.technical.unique.string.customizer.UniqueStringCustomizer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CameraPointColorDescriptionService {
    private final CameraPointColorDescriptionRepository cameraPointColorDescriptionRepository;

    public CameraPointColorDescription createCameraColorDescription(String name){
        String hexCode = improveLineToHexCode(name);
        CameraPointColorDescription cameraPointColorDescription = cameraPointColorDescriptionRepository.findByName(hexCode).orElse(null);
        if(cameraPointColorDescription == null){
            cameraPointColorDescription = CameraPointColorDescription.builder()
                    .name(name)
                    .build();
            cameraPointColorDescription = cameraPointColorDescriptionRepository.save(cameraPointColorDescription);
        }
        return cameraPointColorDescription;
    }

    private String improveLineToHexCode(String line){
        return "#" + line;
    }
}
