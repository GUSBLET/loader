package com.source.loader.model3d.dto;

import com.source.loader.mapper.Mapper;
import com.source.loader.model3d.Model3D;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.zip.GZIPOutputStream;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Model3dPageDTO implements Mapper<Model3dPageDTO, Model3D> {

    private UUID id;

    private String name;

    private String description;

    private String brand;

    private String highPolygonPath;

    private String backgroundPath;

    @Override
    public Model3dPageDTO toDto(Model3D entity) {
        return Model3dPageDTO.builder()
                .name(entity.getName())
                .brand(entity.getBrand().getName())
                .description(entity.getDescription())
                .highPolygonPath(entity.getHighPolygonPath())
                .backgroundPath(entity.getBackgroundPath())
                .id(entity.getId())
                .build();
    }

    @Override
    public Model3D toEntity(Model3dPageDTO dto) {
        return null;
    }
}
