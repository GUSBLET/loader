package com.source.loader.technical.file.strategy;

import com.source.loader.technical.FileNameSeparatorDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.UUID;

public class ShowcaseBackgroundFileProcessing extends AbstractFileProcessing {

    @Override
    public String processSaveFile(MultipartFile file, String directory) {
        FileNameSeparatorDTO dto = removeExtension(Objects.requireNonNull(file.getOriginalFilename()));
        String fileUniqueName = generateUniqueFileName(dto.getName());
        saveFile(file, ABSOLUTE_PATH + directory + "/" + fileUniqueName + dto.getType());
        return fileUniqueName + dto.getType();
    }

    @Override
    public String processUpdateFile(MultipartFile newFile, String currentFilePath, String directory) {
        FileNameSeparatorDTO dto = removeExtension(Objects.requireNonNull(newFile.getOriginalFilename()));
        String fileUniqueName = generateUniqueFileName(dto.getName());
        removeFileByPath(ABSOLUTE_PATH + directory + "/" + currentFilePath);
        saveFile(newFile, ABSOLUTE_PATH + directory + "/" + fileUniqueName + dto.getType());
        return fileUniqueName + dto.getType();
    }
}
