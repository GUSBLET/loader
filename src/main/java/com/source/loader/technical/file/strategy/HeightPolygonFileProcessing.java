package com.source.loader.technical.file.strategy;

import com.source.loader.technical.CachingFile;
import com.source.loader.technical.FileNameSeparatorDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.UUID;

@Component
public class HeightPolygonFileProcessing extends AbstractFileProcessing {
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
        saveFile(newFile, ABSOLUTE_PATH + directory + "/" + fileUniqueName + dto.getType());
        removeFileByPath(ABSOLUTE_PATH + directory + "/" + currentFilePath);
        return fileUniqueName + dto.getType();
    }
}
