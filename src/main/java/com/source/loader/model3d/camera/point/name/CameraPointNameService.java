package com.source.loader.model3d.camera.point.name;

import com.source.loader.technical.unique.string.customizer.UniqueStringCustomizer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CameraPointNameService {
    private final CameraPointNameRepository cameraPointNameRepository;

    public CameraPointName createCameraPointName(String name){
        CameraPointName cameraPointName = cameraPointNameRepository.findByName(UniqueStringCustomizer.capitalizeRecord(name)).orElse(null);
        if(cameraPointName == null){
            cameraPointName = CameraPointName.builder()
                    .name(UniqueStringCustomizer.capitalizeRecord(name))
                    .build();
            cameraPointName = cameraPointNameRepository.save(cameraPointName);
        }
         return cameraPointName;
    }

    public void updateCameraPointNameById(Integer id, String newName){
        if(cameraPointNameRepository.existsById(id)) {
            return;
        }
        cameraPointNameRepository.updateCameraPointNameById(id, newName);
    }



}
