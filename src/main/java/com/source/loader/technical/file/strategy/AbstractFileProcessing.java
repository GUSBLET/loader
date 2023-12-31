package com.source.loader.technical.file.strategy;

import com.source.loader.technical.FileNameSeparatorDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractFileProcessing {

    protected final String ABSOLUTE_PATH;

    protected String generateUniqueFileName(String filename){
        return filename + UUID.randomUUID();
    }

    public abstract String processSaveFile(MultipartFile file, String directory);

    public abstract String processUpdateFile(MultipartFile newFile, String currentFilePath, String directory);

    protected void saveFile(MultipartFile file, String pathToFile) {
        try {
            byte[] bytes = file.getBytes();
            Path path = Path.of(pathToFile);

            Files.write(path, bytes);
        } catch (Exception e) {
            log.error("File saving error:" + e.getMessage());
        }
    }

    protected void removeFileByPath(String stringPath) {
        Path path = Paths.get(stringPath);
        if (Files.exists(path)) {
            try {
                Files.delete(path);
            } catch (IOException e) {
                log.error("File removing error:" + e.getMessage());
            }
        }
    }

    protected FileNameSeparatorDTO removeExtension(String name) {
        int lastDotIndex = name.lastIndexOf('.');
        if(lastDotIndex >= 0){
            return FileNameSeparatorDTO.builder()
                    .name(name.substring(0, lastDotIndex))
                    .type(name.substring(lastDotIndex))
                    .build();
        }
        return FileNameSeparatorDTO.builder().name(name).build();
    }
}
