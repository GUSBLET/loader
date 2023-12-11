package com.source.loader.model3d.dto;

import com.source.loader.mapper.Mapper;
import com.source.loader.model3d.Model3D;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Model3dPageDTO implements Mapper<Model3dPageDTO, Model3D> {

    private String name;

    private String description;

    private String brand;

    private String backgroundPath;

    private String heightPolygonPath;

    @Override
    public Model3dPageDTO toDto(Model3D entity) {
        return Model3dPageDTO.builder()
                .name(entity.getName())
                .brand(entity.getBrand().getName())
                .description(entity.getDescription())
                .heightPolygonPath(entity.getHeightPolygonPath())
                .backgroundPath(entity.getBackgroundPath())
                .build();
    }

    @Override
    public Model3D toEntity(Model3dPageDTO dto) {
        return null;
    }
}
