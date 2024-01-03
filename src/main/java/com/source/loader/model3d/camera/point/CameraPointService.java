package com.source.loader.model3d.camera.point;

import com.source.loader.model3d.camera.point.color.description.CameraPointColorDescription;
import com.source.loader.model3d.camera.point.color.description.CameraPointColorDescriptionService;
import com.source.loader.model3d.camera.point.name.CameraPointName;
import com.source.loader.model3d.camera.point.name.CameraPointNameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CameraPointService {
    private final CameraPointRepository cameraPointRepository;
    private final CameraPointNameService cameraPointNameService;
    private final CameraPointColorDescriptionService cameraPointColorDescriptionService;

    public Set<CameraPoint> createCameraPoints(List<CameraPointDTO> list) {
        Set<CameraPoint> points = new HashSet<>();
        for (CameraPointDTO dto : list) {
            CameraPointName cameraPointName = cameraPointNameService.createCameraPointName(dto.getName());
            CameraPointColorDescription cameraPointColorDescription = cameraPointColorDescriptionService.createCameraColorDescription(dto.getColorDescription());
            points.add(createCameraPoint(dto, cameraPointName, cameraPointColorDescription));
        }
        return points;
    }


    public CameraPoint getCameraPointById(UUID id){
        return cameraPointRepository.findById(id).orElse(null);
    }

    public void updateColorDescription(UUID id, String color){
        if (!cameraPointRepository.existsById(id))
            return;
        CameraPointColorDescription cameraPointColorDescription = cameraPointColorDescriptionService.createCameraColorDescription(color);
        cameraPointRepository.updateCameraPointColorDescriptionById(id, cameraPointColorDescription);
    }



    public void updatePoint(CameraPointDTO dto) {
           CameraPoint cameraPoint = dto.toEntity(dto);
           cameraPoint.setCameraPointName(cameraPointNameService.createCameraPointName(dto.getName()));
           cameraPointRepository.updateCameraPointWithoutColorDescriptionById(cameraPoint.getId(), cameraPoint.getCameraPointName(),
                   cameraPoint.getDescription(), cameraPoint.getPoint_x_position(), cameraPoint.getPoint_y_position(), cameraPoint.getPoint_z_position(),
                   cameraPoint.getCamera_x_position(), cameraPoint.getCamera_y_position(), cameraPoint.getCamera_z_position());
    }

    private CameraPoint createCameraPoint(
            CameraPointDTO dto,
            CameraPointName cameraPointName,
            CameraPointColorDescription cameraPointColorDescription){
        CameraPoint cameraPoint = dto.toEntity(dto);
        cameraPoint.setCameraPointName(cameraPointName);
        cameraPoint.setCameraPointColorDescription(cameraPointColorDescription);
        cameraPointRepository.save(cameraPoint);
        return cameraPoint;
    }
}
