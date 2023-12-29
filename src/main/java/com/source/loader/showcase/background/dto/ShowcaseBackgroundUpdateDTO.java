package com.source.loader.showcase.background.dto;

import com.source.loader.mapper.Mapper;
import com.source.loader.showcase.background.ShowcaseBackground;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ShowcaseBackgroundUpdateDTO implements Mapper<ShowcaseBackgroundUpdateDTO, ShowcaseBackground> {


    private String id;

    private String currentFile;

    private MultipartFile file;

    private String modeName;

    @Override
    public ShowcaseBackgroundUpdateDTO toDto(ShowcaseBackground entity) {
        return ShowcaseBackgroundUpdateDTO.builder()
                .modeName(entity.getModeName())
                .currentFile(entity.getName())
                .id(entity.getId().toString())
                .build();
    }

    @Override
    public ShowcaseBackground toEntity(ShowcaseBackgroundUpdateDTO dto) {
        return ShowcaseBackground.builder()
                .modeName(dto.getModeName())
                .id(UUID.fromString(dto.getId()))
                .name(dto.getFile().getName())
                .build();
    }
}
