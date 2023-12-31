package com.source.loader.model3d.camera.point.dto;


import com.source.loader.mapper.Mapper;
import com.source.loader.model3d.camera.point.CameraPoint;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CameraPointPageDTO implements Mapper<CameraPointPageDTO, CameraPoint> {

    private Double camera_z_position;

    private Double camera_y_position;

    private Double camera_x_position;

    private Double point_z_position;

    private Double point_y_position;

    private Double point_x_position;

    private String description;

    private String colorDescription;

    private String name;

    @Override
    public CameraPointPageDTO toDto(CameraPoint entity) {
        return CameraPointPageDTO.builder()
                .name(entity.getCameraPointName().getName())
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
    public CameraPoint toEntity(CameraPointPageDTO dto) {
        return null;
    }
}
