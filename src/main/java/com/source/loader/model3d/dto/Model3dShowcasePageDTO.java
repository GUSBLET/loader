package com.source.loader.model3d.dto;

import com.source.loader.mapper.Mapper;
import com.source.loader.model3d.Model3D;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Model3dShowcasePageDTO implements Mapper<Model3dShowcasePageDTO, Model3D> {

    private UUID id;

    private String name;

    private String brand;

    private String lowPolygonPath;


    @Override
    public Model3dShowcasePageDTO toDto(Model3D entity) {
        return Model3dShowcasePageDTO.builder()
                .name(entity.getName())
                .brand(entity.getBrand().getName())
                .lowPolygonPath(entity.getHighPolygonPath())
                .id(entity.getId())
                .build();
    }

    @Override
    public Model3D toEntity(Model3dShowcasePageDTO dto) {
        return null;
    }
}
