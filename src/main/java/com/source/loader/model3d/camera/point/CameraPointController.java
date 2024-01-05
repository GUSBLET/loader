package com.source.loader.model3d.camera.point;

import com.source.loader.model3d.camera.point.dto.CameraPointDTO;
import com.source.loader.model3d.camera.point.position.updating.CameraPointPositionUpdatingService;
import com.source.loader.technical.model.attribute.ModelAttributeManager;
import com.source.loader.technical.model.attribute.ModelPageAttributes;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/camera-point")
@Controller
@RequiredArgsConstructor
public class CameraPointController {
    private final CameraPointService cameraPointService;
    private final CameraPointPositionUpdatingService cameraPointPositionUpdatingService;

    @GetMapping("/update-camera-point")
    public String getUpdateCameraPointById(@RequestParam(name = "id") String id, Model model) {
        CameraPoint cameraPoint = cameraPointService.getCameraPointById(UUID.fromString(id));
        if (cameraPoint == null) {
            ModelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                    .title("error")
                    .content("error")
                    .build());
        } else {
            CameraPointDTO dto = new CameraPointDTO();
            dto = dto.toDto(cameraPoint);
            dto.setTechnicalUrl("https://puppetpalm.com/item-technical?cameraId=" + dto.getId() +
                    "&modelId=" + dto.getModel3D().getId() + "&secretKey=" + cameraPointPositionUpdatingService.addSecretKey(UUID.randomUUID().toString()));

            ModelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                    .title("Update camera point")
                    .content("update-camera-point")
                    .entity(dto)
                    .build());
        }
        return "layout";
    }

    @PostMapping("/update-point")
    private String updatePoint(@Valid @ModelAttribute("entity") CameraPointDTO dto, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            ModelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                    .title("Update camera point")
                    .content("update-camera-point")
                    .entity(dto)
                    .build());
        } else {
            cameraPointService.updatePoint(dto);
            ModelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                    .title("success")
                    .content("success")
                    .build());
        }
        return "layout";
    }
}
