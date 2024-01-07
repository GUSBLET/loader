package com.source.loader.technical;

import com.source.loader.model3d.Model3D;
import com.source.loader.model3d.dto.Model3dCreatingDTO;
import com.source.loader.technical.file.strategy.AbstractFileProcessing;
import com.source.loader.technical.file.strategy.BackgroundProcessing;
import com.source.loader.technical.file.strategy.HeightPolygonFileProcessing;
import com.source.loader.technical.file.strategy.LowPolygonFileProcessing;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;
import java.util.zip.GZIPOutputStream;

@Service
@Getter
@RequiredArgsConstructor
public class FileService {
    @Value("${absolute.path}")
    public String ABSOLUTE_PATH;

    public Resource getResource(String directory, String filename){
        Path pathToFolder = Path.of(ABSOLUTE_PATH);
        Path filePath = pathToFolder.resolve(pathToFolder + "/"+ directory + "/" + filename).normalize();
        Resource resource = new PathResource(filePath.toUri());

        if (resource.exists()) {
            return resource;
        }else {
            return null;
        }
    }

    public Model3D saveResources(Model3D model3D, Model3dCreatingDTO dto) {
        AbstractFileProcessing fileProcessing = new BackgroundProcessing();
        createDirectory(model3D.getId(), ABSOLUTE_PATH);

        fileProcessing = new BackgroundProcessing();
        model3D.setBackgroundPathLight(fileProcessing.processSaveFile(dto.getBackgroundPathLight(), model3D.getId().toString()));
        model3D.setBackgroundPathDark(fileProcessing.processSaveFile(dto.getBackgroundPathDark(), model3D.getId().toString()));
        fileProcessing = new LowPolygonFileProcessing();
        model3D.setLowPolygonPath(fileProcessing.processSaveFile(dto.getLowPolygonPath(), model3D.getId().toString()));
        fileProcessing = new HeightPolygonFileProcessing();
        model3D.setHighPolygonPath(fileProcessing.processSaveFile( dto.getHighPolygonPath(), model3D.getId().toString()));
        return model3D;
    }

    public String updateFile(MultipartFile file, String currentFilePath, String directory, AbstractFileProcessing strategy) {
        if (isFileValid(file) || file.isEmpty())
            return currentFilePath;
        return strategy.processUpdateFile(file, currentFilePath, directory.toString());
    }

    private boolean isFileValid(MultipartFile file) {
        return file != null && file.getResource().isFile();
    }

    public void removeModelResourcesById(UUID id){
        try {
            AbstractFileProcessing fileProcessing = new BackgroundProcessing();
            FileUtils.deleteDirectory(new File(ABSOLUTE_PATH + id));
        } catch (IOException e){
                e.printStackTrace();
        }
    }

    private void createDirectory(UUID id, String location) {
        try {
            Path path = Path.of(location + id);
            Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
