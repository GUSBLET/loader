package com.source.loader.showcase.background.dto;

import com.source.loader.mapper.Mapper;
import com.source.loader.showcase.background.ShowcaseBackground;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShowcaseBackgroundDTO implements Mapper<ShowcaseBackgroundDTO, ShowcaseBackground> {

    private String directory;
    private String fileName;
    @Override
    public ShowcaseBackgroundDTO toDto(ShowcaseBackground entity) {
        return ShowcaseBackgroundDTO.builder()
                .fileName(entity.getName())
                .directory("showcaseBackgrounds")
                .build();
    }

    @Override
    public ShowcaseBackground toEntity(ShowcaseBackgroundDTO dto) {
        return null;
    }


}
