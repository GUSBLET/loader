package com.source.loader.model3d.camera.point;

import com.source.loader.model3d.camera.point.color.description.CameraPointColorDescription;
import com.source.loader.model3d.camera.point.color.description.CameraPointColorDescriptionService;
import com.source.loader.model3d.camera.point.dto.CameraPointDTO;
import com.source.loader.model3d.camera.point.dto.CameraPositionUpdateDTO;
import com.source.loader.model3d.camera.point.name.CameraPointName;
import com.source.loader.model3d.camera.point.name.CameraPointNameService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
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
            CameraPointColorDescription cameraPointColorDescription = cameraPointColorDescriptionService.createCameraOrFindColorDescription(dto.getColorDescription());
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
        CameraPointColorDescription cameraPointColorDescription = cameraPointColorDescriptionService.createCameraOrFindColorDescription(color);
        cameraPointRepository.updateCameraPointColorDescriptionById(id, cameraPointColorDescription);
    }

    @Modifying
    @Transactional
    public void removeCameraPointsByModel3dId(UUID id){
        cameraPointRepository.deleteByModel3DId(id);
    }

    public void updatePoint(CameraPointDTO dto) {
           CameraPoint cameraPoint = dto.toEntity(dto);
           cameraPoint.setCameraPointName(cameraPointNameService.createCameraPointName(dto.getName()));
           cameraPointRepository.updateCameraPointWithoutColorDescriptionById(cameraPoint.getId(), cameraPoint.getCameraPointName(),
                   cameraPoint.getDescription());
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

    public boolean updateCameraPositionByNameAndID(CameraPositionUpdateDTO dto) {
        UUID id = tryConvertingStringToUUID(dto.getId());
        if(id == null || cameraPointRepository.findById(id).isEmpty())
            return false;

        double roundedX = Math.round(dto.getPosition_x()* 100.0) / 100.0;
        double roundedY = Math.round(dto.getPosition_y()* 100.0) / 100.0;
        double roundedZ = Math.round(dto.getPosition_z() * 100.0) / 100.0;
        cameraPointRepository.updateCameraPositionById(id, roundedX, roundedY
                , roundedZ );
        return true;
    }

    private float roundingNumberToTheNearestTithe(float number){
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return Float.parseFloat(decimalFormat.format(number));
    }

    private UUID tryConvertingStringToUUID(String line){
        try {
            return UUID.fromString(line);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
