package com.source.loader.showcase.background.dto;

import com.source.loader.mapper.Mapper;
import com.source.loader.showcase.background.ShowcaseBackground;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ShowcaseBackgroundCreatingDTO implements Mapper<ShowcaseBackgroundCreatingDTO, ShowcaseBackground> {

    private MultipartFile file;

    private String modeName;

    @Override
    public ShowcaseBackgroundCreatingDTO toDto(ShowcaseBackground entity) {
        return ShowcaseBackgroundCreatingDTO.builder()
                .modeName(entity.getModeName())
                .build();
    }

    @Override
    public ShowcaseBackground toEntity(ShowcaseBackgroundCreatingDTO dto) {
        return ShowcaseBackground.builder()
                .modeName(dto.getModeName())
                .name(dto.getFile().getName())
                .build();
    }
}
