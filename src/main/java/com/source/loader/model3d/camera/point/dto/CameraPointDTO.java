package com.source.loader.model3d.camera.point.dto;

import com.source.loader.mapper.Mapper;
import com.source.loader.model3d.Model3D;
import com.source.loader.model3d.camera.point.CameraPoint;
import com.source.loader.model3d.camera.point.color.description.CameraPointColorDescription;
import com.source.loader.model3d.camera.point.name.CameraPointName;
import lombok.*;

import java.util.UUID;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CameraPointDTO implements Mapper<CameraPointDTO, CameraPoint> {

    private Double camera_z_position;

    private Double camera_y_position;

    private Double camera_x_position;

    private Double point_z_position;

    private Double point_y_position;

    private Double point_x_position;

    private String description;

    private String colorDescription;

    private String name;

    private UUID id;

    private Model3D model3D;

    private String technicalUrl;

    @Override
    public CameraPointDTO toDto(CameraPoint entity) {
        return CameraPointDTO.builder()
                .id(entity.getId())
                .name(entity.getCameraPointName().getName())
                .model3D(entity.getModel3D())
                .description(entity.getDescription())
                .colorDescription(entity.getCameraPointColorDescription().getName())
                .camera_x_position(entity.getCamera_x_position())
                .camera_y_position(entity.getCamera_y_position())
                .camera_z_position(entity.getCamera_z_position())
                .point_x_position(entity.getPoint_x_position())
                .point_y_position(entity.getPoint_y_position())
                .point_z_position(entity.getPoint_z_position())
                .build();
    }

    @Override
    public CameraPoint toEntity(CameraPointDTO dto) {
        return CameraPoint.builder()
                .id(dto.getId())
                .camera_x_position(dto.getCamera_x_position())
                .camera_y_position(dto.getCamera_y_position())
                .camera_z_position(dto.getCamera_z_position())
                .point_x_position(dto.getPoint_x_position())
                .point_y_position(dto.getPoint_y_position())
                .point_z_position(dto.getPoint_z_position())
                .description(dto.getDescription())
                .cameraPointColorDescription(CameraPointColorDescription.builder().name(dto.getColorDescription()).build())
                .cameraPointName(CameraPointName.builder().name(dto.getName()).build())
                .model3D(dto.getModel3D())
                .build();
    }
}
