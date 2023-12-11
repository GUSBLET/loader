package com.source.loader.technical;

import com.source.loader.model3d.Model3D;
import com.source.loader.model3d.dto.Model3dCreatingDTO;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Getter
public class FileService {

    private final String ABSOLUTE_PATH = "/home/goose/files/";

    public void createDirectory(String directoryName)
    {
        try {
            Files.createDirectories(getPath(directoryName));
        }catch (IOException e){
            System.err.println("Failed to create directory!" + e.getMessage());
        }
    }

    public Model3D saveFiles(Model3D model3D, Model3dCreatingDTO dto){
        final String directoryName = dto.getName() + dto.getBrand();
        createDirectory(directoryName);

        if(!dto.getBackgroundPath().isEmpty()){
            model3D.setBackgroundPath(saveFile(dto.getBackgroundPath(), directoryName));
        }
        model3D.setLowPolygonPath(saveFile(dto.getLowPolygonPath(), directoryName));
        model3D.setHeightPolygonPath(saveFile(dto.getHeightPolygonPath(), directoryName));
        return model3D;
    }



    private String saveFile(MultipartFile file, String directoryName) {
        try {
            byte[] bytes = file.getBytes();
            final String pathToFile = ABSOLUTE_PATH + directoryName + "/" + file.getOriginalFilename();
            Path path = Path.of(pathToFile);

            Files.write(path, bytes);

            return "https://puppetpalm.com/files/" + directoryName + "/" + file.getOriginalFilename();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Path getPath(String directoryName){
        return Paths.get(this.ABSOLUTE_PATH + directoryName);
    }
}
