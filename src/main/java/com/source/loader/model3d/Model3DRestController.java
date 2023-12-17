package com.source.loader.model3d;


import com.source.loader.model3d.dto.Model3dCardDTO;
import com.source.loader.model3d.dto.Model3dPageDTO;
import com.source.loader.technical.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class Model3DRestController {
    private final Model3DService model3DService;
    private FileService fileService;


    @PostMapping("/get-model-page/{id}")
    public ResponseEntity<Model3dPageDTO> getPageData(@PathVariable UUID id){
        Model3dPageDTO dto = model3DService.getModel3dPageById(id);
        if(dto == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping("/get-cards")
    public ResponseEntity<List<Model3dCardDTO>> getCards(){
        return ResponseEntity.status(HttpStatus.OK).body(model3DService.getModel3dCardList());
    }


    @GetMapping("/get-file")
    public ResponseEntity<Resource> downloadFile(@RequestParam String filename) {
        Resource resource = fileService.getResource(filename);
        if(resource.isFile()){
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .body(resource);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/get-page")
    public ResponseEntity<Page<Model3D>> getCardsPageable(@RequestParam(name = "page", defaultValue = "0") int page,
                                                          @RequestParam(name = "size", defaultValue = "20") int size){
        return ResponseEntity.status(HttpStatus.OK).body(model3DService.getTablePage(page, size));
    }

}
