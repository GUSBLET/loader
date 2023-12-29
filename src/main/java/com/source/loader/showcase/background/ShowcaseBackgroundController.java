package com.source.loader.showcase.background;

import com.source.loader.showcase.background.dto.ShowcaseBackgroundCreatingDTO;
import com.source.loader.showcase.background.dto.ShowcaseBackgroundUpdateDTO;
import com.source.loader.technical.model.attribute.ModelAttributeManager;
import com.source.loader.technical.model.attribute.ModelPageAttributes;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/showcase-background")
@RequiredArgsConstructor
public class ShowcaseBackgroundController {
    private final ShowcaseBackgroundService backgroundService;
    private final ModelAttributeManager modelAttributeManager;

    @GetMapping("/creating-form")
    public String getCreatingForm(Model model) {
        modelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                .title("creating background")
                .content("creating-background-form")
                .entity(new ShowcaseBackgroundCreatingDTO())
                .build());
        return "layout";
    }


    @GetMapping("/show-more")
    public String getShowMore(@RequestParam UUID id, Model model) {
        ShowcaseBackgroundUpdateDTO dto = backgroundService.getShowcaseBackgroundPageById(id);
        if (dto == null) {
            modelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                    .title("error")
                    .content("error")
                    .build());
        } else {
            modelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                    .title(dto.getModeName())
                    .content("showcase-background-show-more")
                    .entity(dto)
                    .build());
        }
        return "layout";
    }

    @PostMapping("/update-background")
    private  String updateBackground(@Valid @ModelAttribute("entity") ShowcaseBackgroundUpdateDTO dto,
                                     BindingResult bindingResult,
                                     Model model){
        if(bindingResult.hasErrors()){
            modelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                    .title(dto.getModeName())
                    .content("showcase-background-show-more")
                    .entity(dto)
                    .build());
            return "layout";
        }
        ShowcaseBackground background = backgroundService.findBackgroundByName(dto.getModeName());
        if (background != null && !Objects.equals(background.getId(), dto.getId())) {
            modelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                    .title(dto.getCurrentFile())
                    .content("showcase-background-show-more")
                    .entity(dto)
                    .build());

            bindingResult.rejectValue("modeName", "entity", background.getModeName() + " mode exists" );
            return "layout";
        }
        backgroundService.updateShowcaseBackground(dto);
        modelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                .title("success")
                .content("success")
                .build());

        return "layout";
    }

    @PostMapping("/delete-new-confirming")
    @ResponseBody
    public boolean removeModel3d(@RequestParam String id) {
        return  backgroundService.removeModel(UUID.fromString(id));
    }

    @PostMapping("/create-background")
    public String getCreateForm(@Valid @ModelAttribute("entity") ShowcaseBackgroundCreatingDTO dto,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            modelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                    .title("creating background")
                    .content("creating-background-form")
                    .entity(dto)
                    .build());
            return "layout";
        }

        if (backgroundService.createShowcaseBackground(dto)) {
            modelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                    .title("success")
                    .content("success")
                    .build());
        } else {
            modelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                    .title("creating background")
                    .content("creating-background-form")
                    .entity(dto)
                    .build());
            bindingResult.rejectValue("modeName", "entity", "Name mode already exists");
        }
        return "layout";
    }

    @GetMapping("/controller-panel")
    private String getControllerBackgroundPanel(@RequestParam(name = "page", defaultValue = "0") int page,
                                      @RequestParam(name = "size", defaultValue = "20") int size,
                                      Model model) {

        modelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                .title("creating background")
                .content("controller-background-panel")
                .entity(backgroundService.getTablePage(page, size))
                .build());

        return "layout";
    }
}
