package com.source.loader.model3d.dto;

import com.source.loader.mapper.Mapper;
import com.source.loader.model3d.Model3D;
import com.source.loader.model3d.camera.point.CameraPoint;
import com.source.loader.model3d.camera.point.CameraPointDTO;
import com.source.loader.model3d.camera.point.CameraPointPageDTO;
import lombok.*;

import java.util.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Model3dPageDTO implements Mapper<Model3dPageDTO, Model3D> {

    private UUID id;

    private String name;

    private String brand;

    private String description;

    private String backgroundPathLight;

    private String backgroundPathDark;

    private String highPolygonPath;

    private List<CameraPointPageDTO> cameraPoints;

    @Override
    public Model3dPageDTO toDto(Model3D entity) {
        CameraPointDTO dto = new CameraPointDTO();
        List<CameraPointPageDTO> pointDTO = convertEntitySetToDTOList(entity.getCameraPoints());

        return Model3dPageDTO.builder()
                .id(entity.getId())
                .brand(entity.getBrand().getName())
                .name(entity.getName())
                .description(entity.getDescription())
                .backgroundPathLight(entity.getBackgroundPathLight())
                .backgroundPathDark(entity.getBackgroundPathDark())
                .highPolygonPath(entity.getHighPolygonPath())
                .cameraPoints(pointDTO)
                .build();
    }

    @Override
    public Model3D toEntity(Model3dPageDTO dto) {
        return null;
    }

    private List<CameraPointPageDTO> convertEntitySetToDTOList(Set<CameraPoint> set){
        List<CameraPointPageDTO> list = new ArrayList<>();
        CameraPointPageDTO  dto = new CameraPointPageDTO();
        for (CameraPoint cameraPoint : set) {
            list.add(dto.toDto(cameraPoint));
        }
        return list;
    }
}
