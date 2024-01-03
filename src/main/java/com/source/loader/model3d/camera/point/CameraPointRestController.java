package com.source.loader.model3d.camera.point;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/camera-point")
public class CameraPointRestController {
    private final CameraPointService cameraPointService;

    @GetMapping("/update-camera-point-description-color")
    public void updateCameraPointDescriptionColor(@RequestParam(name = "id") String id,
                                                  @RequestParam(name = "selectedColor") String selectedColor){
        if(id == null || selectedColor == null)
            return;
        cameraPointService.updateColorDescription(UUID.fromString(id), selectedColor);
    }
}



