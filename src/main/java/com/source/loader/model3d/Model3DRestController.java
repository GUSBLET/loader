package com.source.loader.model3d;


import com.source.loader.model3d.dto.Model3dPageDTO;
import com.source.loader.model3d.dto.Model3dShowcasePageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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


    @PostMapping("/get-model-page/{id}")
    public ResponseEntity<Model3dPageDTO> getPageData(@PathVariable UUID id){
        Model3dPageDTO dto = model3DService.getModel3dPageById(id);
        if(dto == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping("/get-cards")
    public ResponseEntity<List<Model3dShowcasePageDTO>> getCards(){
        return ResponseEntity.status(HttpStatus.OK).body(model3DService.getModel3dCardList());
    }

    
    @GetMapping("/get-showcase-page")
    public ResponseEntity<Page<Model3dShowcasePageDTO>> getCardsPageable(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                         @RequestParam(name = "size", defaultValue = "8") int size){
        Page<Model3D> buffer = model3DService.getTablePage(page, size);
        Model3dShowcasePageDTO dto = new Model3dShowcasePageDTO();
        Page<Model3dShowcasePageDTO> result = buffer.map(dto::toDto);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
