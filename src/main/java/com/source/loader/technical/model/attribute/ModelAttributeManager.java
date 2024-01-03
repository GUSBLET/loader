package com.source.loader.technical.model.attribute;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class ModelAttributeManager {

    public static void setModelAttribute(Model model, ModelPageAttributes attributes){
        model.addAttribute("title", attributes.getTitle());
        model.addAttribute("entity", attributes.getEntity());
        model.addAttribute("content", attributes.getContent());
    }
}
