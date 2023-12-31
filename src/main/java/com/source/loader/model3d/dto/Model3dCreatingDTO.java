package com.source.loader.model3d.dto;

import com.source.loader.brand.Brand;
import com.source.loader.mapper.Mapper;
import com.source.loader.model3d.Model3D;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Model3dCreatingDTO implements Mapper<Model3dCreatingDTO, Model3D> {

    @NotBlank(message = "Enter your name")
    private String name;

    @NotBlank(message = "Enter your brand")
    private String brand;

    private String description;

    private Long priority;

    @NotNull(message = "Enter file height polygon model")
    private MultipartFile highPolygonPath;

    @NotNull(message = "Enter file light mode background of model")
    private MultipartFile backgroundPathLight;

    @NotNull(message = "Enter file dark mode background of model")
    private MultipartFile backgroundPathDark;

    @NotNull(message = "Enter file low polygon model")
    private MultipartFile lowPolygonPath;

    @Override
    public Model3dCreatingDTO toDto(Model3D entity) {
        return Model3dCreatingDTO.builder()
                .brand(entity.getBrand().getName())
                .name(entity.getName())
                .priority(entity.getPriority())
                .description(entity.getDescription())
                .build();
    }

    @Override
    public Model3D toEntity(Model3dCreatingDTO dto) {
        return Model3D.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .brand(Brand.builder().name(dto.getBrand()).build())
                .priority(dto.getPriority())
                .build();
    }
}
