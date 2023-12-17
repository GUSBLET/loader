package com.source.loader.technical;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

@Component
public class CachingFile {

    public void saveFileInGzip(String pathToFile, String fileType){
        try (
                FileInputStream fis = new FileInputStream(pathToFile + fileType);
                GZIPOutputStream gzipOut = new GZIPOutputStream(new FileOutputStream(pathToFile + ".gz"))
        ) {
            byte[] buffer = new byte[(int) getBufferLength(pathToFile + fileType)];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) > 0) {
                gzipOut.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private long getBufferLength(String pathToFile){
        return new File(pathToFile).length();
    }

}
