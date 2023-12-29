package com.source.loader.showcase.background;

import com.source.loader.model3d.dto.Model3dPageDTO;
import com.source.loader.showcase.background.dto.ShowcaseBackgroundDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/background-api")
public class ShowcaseBackgroundRestController {
    private final ShowcaseBackgroundService backgroundService;


    @GetMapping("/get-showcase-background")
    public ResponseEntity<ShowcaseBackgroundDTO> getShowcaseBackground(@RequestParam(name = "name") String name) {
        ShowcaseBackground showcaseBackground = backgroundService.findBackgroundByModeName(name);
        if(showcaseBackground == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        ShowcaseBackgroundDTO dto = new ShowcaseBackgroundDTO();
        dto = dto.toDto(showcaseBackground);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
}
