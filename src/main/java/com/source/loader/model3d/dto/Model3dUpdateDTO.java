package com.source.loader.model3d.dto;

import com.source.loader.brand.Brand;
import com.source.loader.mapper.Mapper;
import com.source.loader.model3d.Model3D;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Model3dUpdateDTO implements Mapper<Model3dUpdateDTO, Model3D> {

    private UUID id;

    @NotBlank(message = "Enter your name")
    private String name;

    @NotBlank(message = "Enter your brand")
    private String brand;

    private String description;

    private String currentBackgroundPath;

    private String currentHeightPolygonPath;

    private String currentLowPolygonPath;

    private MultipartFile heightPolygonPath;

    private MultipartFile backgroundPath;

    private MultipartFile lowPolygonPath;

    @Override
    public Model3dUpdateDTO toDto(Model3D entity) {
        return Model3dUpdateDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .currentBackgroundPath(entity.getBackgroundPath())
                .currentLowPolygonPath(entity.getLowPolygonPath())
                .currentHeightPolygonPath(entity.getHeightPolygonPath())
                .brand(entity.getBrand().getName())
                .build();
    }

    @Override
    public Model3D toEntity(Model3dUpdateDTO dto) {
        return Model3D.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .brand(Brand.builder().name(dto.getBrand()).build())
                .build();
    }
}
