package com.source.loader.model3d.camera.point.dto;

import com.source.loader.mapper.Mapper;
import com.source.loader.model3d.camera.point.CameraPoint;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CameraPositionUpdateDTO implements Mapper<CameraPositionUpdateDTO, CameraPoint> {
    private String id;
    private String secretKey;
    private Double position_x;
    private Double position_y;
    private Double position_z;

    @Override
    public CameraPositionUpdateDTO toDto(CameraPoint entity) {
        return null;
    }

    @Override
    public CameraPoint toEntity(CameraPositionUpdateDTO dto) {
        return CameraPoint.builder().build();
    }
}
