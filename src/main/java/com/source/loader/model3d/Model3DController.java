package com.source.loader.model3d;

import com.source.loader.model3d.dto.Model3dCreatingDTO;
import com.source.loader.model3d.dto.Model3dUpdateDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/model3d")
@RequiredArgsConstructor
public class Model3DController {
    private final Model3DService model3DService;

    @GetMapping("/controller-panel")
    private String getControllerPanel(@RequestParam(name = "page", defaultValue = "0") int page,
                                      @RequestParam(name = "size", defaultValue = "20") int size,
                                      Model model) {
        Page<Model3D> model3DPage = model3DService.getTablePage(page, size);
        model.addAttribute("title", "panel");
        model.addAttribute("model3DPage", model3DPage);
        model.addAttribute("content", "controller-panel");
        return "layout";
    }

    @GetMapping("/show-more")
    public String getShowMore(@RequestParam UUID id, Model model) {
        Model3dUpdateDTO dto = model3DService.getModel3dUpdateDTO(id);
        if (dto == null) {
            model.addAttribute("title", "error");
            model.addAttribute("content", "error");
        } else {
            model.addAttribute("title", dto.getName());
            model.addAttribute("content", "show-more");
            model.addAttribute("model", dto);
        }
        return "layout";
    }

    @PostMapping("/update-model")
    public String updateModel(@Valid @ModelAttribute("model") Model3dUpdateDTO dto,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("content", "error");
            model.addAttribute("title", dto.getName());
            return "layout";
        }
        Model3D model3d = model3DService.findModelByName(dto.getName());
        if (model3d != null && !Objects.equals(model3d.getId(), dto.getId())) {
            model.addAttribute("title", dto.getName());
            model.addAttribute("content", "show-more");
            bindingResult.rejectValue("name", "error","This name exists");
            model.addAttribute("model", dto.toDto(model3d));
            return "layout";
        }

        model3DService.updateModel3d(dto);
        model.addAttribute("title", "success");
        model.addAttribute("content", "success");

        return "layout";
    }

    @PostMapping("/delete-new-confirming")
    @ResponseBody
    public boolean removeModel3d(@RequestParam UUID id){
        model3DService.removeModel(id);
        return true;
    }

    @GetMapping("/create-model-form")
    private String getModelForm(Model model) {
        model.addAttribute("title", "create");
        model.addAttribute("model", new Model3dCreatingDTO());
        model.addAttribute("content", "create-model-form");
        return "layout";
    }

    @PostMapping("/create-model")
    private String createModel(@Valid @ModelAttribute("model") Model3dCreatingDTO dto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "create");
            model.addAttribute("model", dto);
            model.addAttribute("content", "create-model-form");
            return "layout";
        }
        if (model3DService.createModel(dto)) {
            model.addAttribute("title", "success");
            model.addAttribute("content", "success");
            model.addAttribute("message", "");
        } else {
            model.addAttribute("title", "create");
            model.addAttribute("model", dto);
            model.addAttribute("content", "create-model-form");
            bindingResult.rejectValue("name", "error","secret key is wrong");
        }

        return "layout";
    }


}
