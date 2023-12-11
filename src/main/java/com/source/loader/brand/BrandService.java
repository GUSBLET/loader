package com.source.loader.brand;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;

    public boolean isBrandExist(String name){
        return brandRepository.existsByName(name);
    }

    public void createNewBrand(String name){
        if(brandRepository.existsByName(name))
            return;
        Brand brand = Brand.builder()
                .name(capitalizeBrandName(name))
                .build();
        brandRepository.save(brand);
    }

    public Brand findBrandByName(String name){
        return brandRepository.findByName(capitalizeBrandName(name)).get();
    }

    private String capitalizeBrandName(String name){
        if (name != null && !name.isEmpty())
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        return name;
    }
}
