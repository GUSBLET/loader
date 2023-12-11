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
public class Model3dCardDTO implements Mapper<Model3dCardDTO, Model3D> {

    private Long id;

    private String name;

    private String brand;

    private String lowPolygonPath;

    @Override
    public Model3dCardDTO toDto(Model3D entity) {
        return Model3dCardDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .brand(entity.getBrand().getName())
                .lowPolygonPath(entity.getLowPolygonPath())
                .build();
    }

    @Override
    public Model3D toEntity(Model3dCardDTO dto) {
        return null;
    }
}
