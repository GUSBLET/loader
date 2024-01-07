package com.source.loader.showcase.background;


import com.source.loader.showcase.background.dto.ShowcaseBackgroundCreatingDTO;
import com.source.loader.showcase.background.dto.ShowcaseBackgroundUpdateDTO;
import com.source.loader.technical.FileService;
import com.source.loader.technical.file.strategy.AbstractFileProcessing;
import com.source.loader.technical.file.strategy.BackgroundProcessing;
import com.source.loader.technical.file.strategy.ShowcaseBackgroundFileProcessing;
import com.source.loader.technical.unique.string.customizer.UniqueStringCustomizer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShowcaseBackgroundService {
    private final ShowcaseBackgroundRepository showcaseBackgroundRepository;
    private final FileService fileService;

    @Value("${background-directory}")
    private String DIRECTORY_NAME;

    public boolean createShowcaseBackground(ShowcaseBackgroundCreatingDTO dto) {
        dto.setModeName(UniqueStringCustomizer.capitalizeRecord(dto.getModeName()));
        if (showcaseBackgroundRepository.existsByModeName(dto.getModeName()))
            return false;

        AbstractFileProcessing fileProcessing = new ShowcaseBackgroundFileProcessing(fileService.ABSOLUTE_PATH);
        String fileName = fileProcessing.processSaveFile(dto.getFile(), DIRECTORY_NAME);
        ShowcaseBackground showcaseBackground = dto.toEntity(dto);
        showcaseBackground.setName(fileName);
        showcaseBackgroundRepository.save(showcaseBackground);
        return true;
    }

    public Page<ShowcaseBackground> getTablePage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return showcaseBackgroundRepository.findAll(pageable);
    }

    public ShowcaseBackgroundUpdateDTO getShowcaseBackgroundPageById(UUID id) {
        Optional<ShowcaseBackground> background = showcaseBackgroundRepository.findById(id);
        if (background.isEmpty()) {
            return null;
        }

        ShowcaseBackgroundUpdateDTO dto = new ShowcaseBackgroundUpdateDTO();
        return dto.toDto(background.get());
    }


    public void updateShowcaseBackground(ShowcaseBackgroundUpdateDTO dto) {
        dto.setModeName(UniqueStringCustomizer.capitalizeRecord(dto.getModeName()));
        ShowcaseBackground showcaseBackground = dto.toEntity(dto);
        showcaseBackground.setName(fileService.updateFile(dto.getFile(), dto.getCurrentFile(), DIRECTORY_NAME, new BackgroundProcessing(fileService.ABSOLUTE_PATH)));
        showcaseBackgroundRepository.updateShowcaseBackgroundById(showcaseBackground.getId(), showcaseBackground.getModeName(), showcaseBackground.getName());
    }

    public ShowcaseBackground findBackgroundByModeName(String modeName) {
        return showcaseBackgroundRepository.findByModeName(UniqueStringCustomizer.capitalizeRecord(modeName)).orElse(null);
    }

    public boolean removeModel(UUID id) {
        Optional<ShowcaseBackground> background = showcaseBackgroundRepository.findById(id);
        if (background.isEmpty())
            return false;
        showcaseBackgroundRepository.delete(background.get());
        return true;
    }
}
