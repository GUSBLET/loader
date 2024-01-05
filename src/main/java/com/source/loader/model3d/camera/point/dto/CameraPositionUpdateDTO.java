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
    private float position_x;
    private float position_y;
    private float position_z;

    @Override
    public CameraPositionUpdateDTO toDto(CameraPoint entity) {
        return null;
    }

    @Override
    public CameraPoint toEntity(CameraPositionUpdateDTO dto) {
        return CameraPoint.builder().build();
    }
}
