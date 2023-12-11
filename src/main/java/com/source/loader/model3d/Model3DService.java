package com.source.loader.model3d;

import com.source.loader.brand.Brand;
import com.source.loader.brand.BrandService;
import com.source.loader.model3d.dto.Model3dCardDTO;
import com.source.loader.model3d.dto.Model3dCreatingDTO;
import com.source.loader.model3d.dto.Model3dPageDTO;
import com.source.loader.technical.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Model3DService {
    private final Model3DRepository model3DRepository;
    private final BrandService brandService;
    private final FileService fileService;

    public boolean createModel(Model3dCreatingDTO dto) {
        brandService.createNewBrand(dto.getBrand());
        Brand brand = brandService.findBrandByName(dto.getBrand());

        if (model3DRepository.existsByName(dto.getBrand()))
            return false;

        Model3D model3D = dto.toEntity(dto);
        model3D.setBrand(brand);
        model3D = fileService.saveFiles(model3D, dto);

        model3DRepository.save(model3D);
        return true;
    }

    public List<Model3dCardDTO> getModel3dCardList() {
        List<Model3D> result = model3DRepository.findTop12ByOrderByIdDesc();
        Model3dCardDTO dto = new Model3dCardDTO();
        return dto.toDtoList(result);
    }

    public Model3dPageDTO getModel3dPageById(Long id) {
         Optional<Model3D> model3D = model3DRepository.findById(id);
         if(model3D.isPresent()){
             Model3dPageDTO dto = new Model3dPageDTO();
             return dto.toDto(model3D.get());
         }
         return null;
    }
}
