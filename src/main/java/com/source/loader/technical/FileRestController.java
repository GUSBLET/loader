package com.source.loader.technical;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileRestController {

    private final FileService fileService;

    @GetMapping("/get-file")
    public ResponseEntity<Resource> downloadFile(@RequestParam String directory,
                                                 @RequestParam String filename) {
        Resource resource =
                fileService.getResource(directory, filename);
        if(resource.isFile()){
            return ResponseEntity.ok()
                    .body(resource);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}
