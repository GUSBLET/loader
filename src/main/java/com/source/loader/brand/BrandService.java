package com.source.loader.brand;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;

    public boolean isBrandExist(String name){
        return brandRepository.existsByName(name);
    }

    @Modifying
    @Transactional
    public Brand updateBrand(String brandName){
        Brand brand = findBrandByName(brandName);
        if(brand != null)
            return brand;
        brand = Brand.builder().name(brandName).build();
        brandRepository.save(brand);
        return brand;
    }

    public void createNewBrand(String name){
        if(brandRepository.existsByName(name))
            return;
        Brand brand = Brand.builder()
                .name(name)
                .build();
        brandRepository.save(brand);
    }

    public Brand findBrandByName(String name){
        return brandRepository.findByName(name).orElse(null);
    }


}
