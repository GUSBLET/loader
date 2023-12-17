package com.source.loader.technical;

import com.source.loader.model3d.Model3D;
import com.source.loader.model3d.dto.Model3dCreatingDTO;
import lombok.Getter;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;
import java.util.zip.GZIPOutputStream;
import org.springframework.core.io.Resource;

@Service
@Getter
public class FileService {

    @Value("${folder.splitter}")
    private String FOLDER_SPLITTER;
    @Value("${folder.path}")
    private String ABSOLUTE_PATH;
    @Value("${folder.network.path}")
    private String NETWORK_PATH;


    public Resource getResource(String filename){
        try {
            Path pathToFolder = Path.of(this.ABSOLUTE_PATH);
            Path filePath = pathToFolder.resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            }else {
                return null;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Model3D saveResources(Model3D model3D, Model3dCreatingDTO dto) {

        createDirectory(model3D.getId());

        if (!dto.getBackgroundPath().isEmpty()) {
            model3D.setBackgroundPath(saveFile(dto.getBackgroundPath(),
                    createAbsoluteFilePath(
                            model3D.getId(),
                            dto.getBackgroundPath()
                                    .getOriginalFilename())));
        }
        model3D.setLowPolygonPath(saveFileInGzip(
                saveFile(dto.getLowPolygonPath(),
                        createAbsoluteFilePath(
                                model3D.getId(),
                                dto.getLowPolygonPath()
                                        .getOriginalFilename()))));

        model3D.setHeightPolygonPath(
                saveFileInGzip(saveFile(dto.getHeightPolygonPath(),
                        createAbsoluteFilePath(
                                model3D.getId(),
                                dto.getHeightPolygonPath()
                                        .getOriginalFilename()))));
        return model3D;
    }


    public void rewriteFile(MultipartFile file,String newFilePath, String currentFilePath) {
        ChangeFilePathDTO dto = removeExtension(convertNetworkPathToAbsolutePath(currentFilePath));
        removeFileByPath(dto.getName() + dto.getType());
        removeFileByPath(dto.getName() + ".glb");
        saveFileInGzip(saveFile(file, newFilePath + ".glb"));
    }

    public String updateFile(MultipartFile file, String currentFilePath, UUID id) {
        ChangeFilePathDTO dto = null;
        if (isFileValid(file))
            return currentFilePath;
        if (!file.isEmpty()) {
            String newFilePath = changeFileIfFIlePathExist(
                    removeExtension(Objects.requireNonNull(file.getOriginalFilename())),
                    id);
            rewriteFile(file, newFilePath, currentFilePath);
            dto = removeExtension(newFilePath);
        }
        
        return convertAbsolutePathToNetworkPath(dto == null ? currentFilePath : dto.getName() + ".gz");
    }



    private String changeFileIfFIlePathExist(ChangeFilePathDTO dto, UUID id) {
        String path = this.ABSOLUTE_PATH + id + this.FOLDER_SPLITTER + dto.getName();
        if (Files.exists(Paths.get(path + dto.getType())))
            path = path + UUID.randomUUID() + dto.getType();
        return path;
    }

    private boolean isFileValid(MultipartFile file) {
        return file != null && file.getResource().isFile();
    }
    private ChangeFilePathDTO removeExtension(String name) {
        int lastDotIndex = name.lastIndexOf('.');
        if(lastDotIndex >= 0){
            return ChangeFilePathDTO.builder()
                    .name(name.substring(0, lastDotIndex))
                    .type(name.substring(lastDotIndex))
                    .build();
        }
        return ChangeFilePathDTO.builder().name(name).build();

    }

    public void removeModelResources(Model3D model3D){
        try {
            FileUtils.deleteDirectory(new File(this.ABSOLUTE_PATH + model3D.getId()));
        } catch (IOException e){
                e.printStackTrace();
        }
    }

    private void removeFileByPath(String stringPath) {
        Path path = Paths.get(stringPath);
        if (Files.exists(path)) {
            try {
                Files.delete(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String createAbsoluteFilePath(UUID id, String path) {
        return ABSOLUTE_PATH + id + this.FOLDER_SPLITTER + path;
    }

    private String convertAbsolutePathToNetworkPath(String path){
        return path.replace(this.ABSOLUTE_PATH, this.NETWORK_PATH);
    }

    private String convertNetworkPathToAbsolutePath(String path){
        return path.replace(this.NETWORK_PATH, this.ABSOLUTE_PATH);
    }

    private String saveFileInGzip(String pathToFile){
        ChangeFilePathDTO dto = removeExtension(pathToFile);
        try (
                FileInputStream fis = new FileInputStream(pathToFile);
                GZIPOutputStream gzipOut = new GZIPOutputStream(new FileOutputStream(dto.getName() + ".gz"))
        ) {
            byte[] buffer = new byte[(int) getBufferLength(pathToFile)];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) > 0) {
                gzipOut.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convertAbsolutePathToNetworkPath(dto.getName() + ".gz");
    }

    private long getBufferLength(String pathToFile){
        File file = new File(pathToFile);

        return file.length();
    }

    private String saveFile(MultipartFile file, String pathToFile) {
        try {
            byte[] bytes = file.getBytes();
            Path path = Path.of(pathToFile);

            Files.write(path, bytes);

            return pathToFile;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void createDirectory(UUID id) {
        try {
            Files.createDirectories(getPath(id));
        } catch (IOException e) {
            System.err.println("Failed to create directory!" + e.getMessage());
        }
    }

    private Path getPath(UUID id) {
        return Paths.get(this.ABSOLUTE_PATH + id.toString());
    }
}
