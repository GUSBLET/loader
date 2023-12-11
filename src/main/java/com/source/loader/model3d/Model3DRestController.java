package com.source.loader.model3d;


import com.source.loader.model3d.dto.Model3dCardDTO;
import com.source.loader.model3d.dto.Model3dCreatingDTO;
import com.source.loader.model3d.dto.Model3dPageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class Model3DRestController {
    private final Model3DService model3DService;


    @PostMapping("/get-model-page/{id}")
    public ResponseEntity<Model3dPageDTO> getPageData(@PathVariable long id){
        Model3dPageDTO dto = model3DService.getModel3dPageById(id);
        if(dto == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping("/get-cards")
    public ResponseEntity<List<Model3dCardDTO>> getCards(){
        return ResponseEntity.status(HttpStatus.OK).body(model3DService.getModel3dCardList());
    }

}
