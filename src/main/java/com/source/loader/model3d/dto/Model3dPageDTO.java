package com.source.loader.model3d.dto;

import com.source.loader.mapper.Mapper;
import com.source.loader.model3d.Model3D;
import lombok.*;

import java.util.UUID;

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

    private String backgroundPath;

    private String highPolygonPath;

    @Override
    public Model3dPageDTO toDto(Model3D entity) {
        return Model3dPageDTO.builder()
                .id(entity.getId())
                .brand(entity.getBrand().getName())
                .name(entity.getName())
                .description(entity.getDescription())
                .backgroundPath(entity.getBackgroundPath())
                .highPolygonPath(entity.getHighPolygonPath())
                .build();
    }

    @Override
    public Model3D toEntity(Model3dPageDTO dto) {
        return null;
    }
}
