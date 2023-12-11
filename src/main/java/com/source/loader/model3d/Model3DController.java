package com.source.loader.model3d;

import com.source.loader.model3d.dto.Model3dCreatingDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/model3d")
@RequiredArgsConstructor
public class Model3DController {
    private final Model3DService model3DService;

    @GetMapping("/controller-panel")
    private String getControllerPanel(Model model){
        model.addAttribute("title", "panel");
        return "model3d/controller-panel";
    }

    @GetMapping("/create-model-form")
    private String getModelForm(Model model){
        model.addAttribute("title", "create");
        model.addAttribute("model", new Model3dCreatingDTO());
        return "model3d/create-model-form";
    }

    @PostMapping("/create-model")
    private String createModel(@Valid @ModelAttribute("model") Model3dCreatingDTO  dto, BindingResult bindingResult, Model model){
        model.addAttribute("title", "create");
        if (bindingResult.hasErrors()) {
            model.addAttribute("model", dto);
            return "model3d/create-model-form";
        }
        if(model3DService.createModel(dto))
            return "model3d/create-model-form";
        return "fragments/error";
    }
}
