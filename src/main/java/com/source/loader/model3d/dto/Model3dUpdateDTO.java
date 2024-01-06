package com.source.loader.model3d.dto;

import com.source.loader.brand.Brand;
import com.source.loader.mapper.Mapper;
import com.source.loader.model3d.Model3D;
import com.source.loader.model3d.camera.point.CameraPoint;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

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

    private String currentBackgroundPathLight;

    private String currentBackgroundPathDark;

    private String currentHighPolygonPath;

    private String currentLowPolygonPath;

    private MultipartFile highPolygonPath;

    private MultipartFile backgroundPathLight;

    private MultipartFile backgroundPathDark;

    private MultipartFile lowPolygonPath;

    private List<CameraPoint> cameraPoints;

    @Override
    public Model3dUpdateDTO toDto(Model3D entity) {
        return Model3dUpdateDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .currentBackgroundPathLight(entity.getBackgroundPathLight())
                .currentBackgroundPathDark(entity.getBackgroundPathDark())
                .currentLowPolygonPath(entity.getLowPolygonPath())
                .currentHighPolygonPath(entity.getHighPolygonPath())
                .brand(entity.getBrand().getName())
                .cameraPoints(convertSetToList(entity.getCameraPoints()))
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

    private List<CameraPoint> convertSetToList(Set<CameraPoint> set){
        List<CameraPoint> list = new ArrayList<>(set);
        list.sort(Comparator.comparing(cameraPoint -> cameraPoint.getCameraPointName().getName()));
        return list;
    }
}
