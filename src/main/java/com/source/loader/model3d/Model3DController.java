package com.source.loader.model3d;

import com.source.loader.model3d.dto.Model3dCreatingDTO;
import com.source.loader.model3d.dto.Model3dShowcasePageDTO;
import com.source.loader.model3d.dto.Model3dUpdateDTO;
import com.source.loader.technical.model.attribute.ModelAttributeManager;
import com.source.loader.technical.model.attribute.ModelPageAttributes;
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
    private final ModelAttributeManager modelAttributeManager;

    @PostMapping("/delete-new-confirming")
    @ResponseBody
    public boolean removeModel3d(@RequestParam String id) {
        model3DService.removeModel(UUID.fromString(id));
        return true;
    }

    @GetMapping("/controller-panel")
    private String getControllerPanel(@RequestParam(name = "page", defaultValue = "0") int page,
                                      @RequestParam(name = "size", defaultValue = "20") int size,
                                      Model model) {
        Page<Model3D> model3DPage = model3DService.getTablePage(page, size);
        modelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                .title("panel")
                .content("controller-panel")
                .entity(model3DPage)
                .build());
        return "layout";
    }

    @GetMapping("/show-more")
    public String getShowMore(@RequestParam UUID id, Model model) {
        Model3dUpdateDTO dto = model3DService.getModel3dUpdateDTO(id);
        if (dto == null) {
            modelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                    .title("error")
                    .content("error")
                    .build());
        } else {
            modelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                    .title(dto.getName())
                    .content("show-more")
                    .entity(dto)
                    .build());
        }
        return "layout";
    }

    @PostMapping("/update-priority")
    public  String updatePriority(@RequestParam(name = "id") String id,
                                  @RequestParam(name = "priority") Long priority,
                                  @RequestParam(name = "lastPriority") Long lastPriority){
        model3DService.updateModelPriorityById(id, priority, lastPriority);
        return "redirect:https://puppetpalm.com:9999/model3d/controller-panel";
    }

    @PostMapping("/update-model")
    public String updateModel(@Valid @ModelAttribute("entity") Model3dUpdateDTO dto,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            modelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                    .title("error")
                    .content("error")
                    .build());

            return "layout";
        }
        Model3D model3d = model3DService.findModelByName(dto.getName());
        if (model3d != null && !Objects.equals(model3d.getId(), dto.getId())) {
            modelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                    .title(dto.getName())
                    .content("show-more")
                    .entity(dto)
                    .build());

            bindingResult.rejectValue("name", "entity", model3d.getName()+ " exists");
            return "layout";
        }

        model3DService.updateModel3d(dto);
        modelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                .title("success")
                .content("success")
                .build());

        return "layout";
    }

    @GetMapping("/create-model-form")
    private String getModelForm(Model model) {

        modelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                .title("create")
                .content("create-model-form")
                .entity(model3DService.getLastModelPriority())
                .build());

        return "layout";
    }

    @PostMapping("/create-model")
    private String createModel(@Valid @ModelAttribute("entity") Model3dCreatingDTO dto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            modelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                    .title("create")
                    .content("create-model-form")
                    .entity(dto)
                    .build());
            return "layout";
        }
        if (model3DService.createModel(dto)) {
            modelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                    .title("success")
                    .content("success")
                    .build());
        } else {
            modelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                    .title("create")
                    .content("create-model-form")
                    .entity(dto)
                    .build());
            bindingResult.rejectValue("name", "entity", "name already exists");
        }

        return "layout";
    }
}
