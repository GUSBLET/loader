package com.source.loader.model3d;

import com.source.loader.brand.Brand;
import com.source.loader.brand.BrandService;
import com.source.loader.model3d.dto.Model3dCreatingDTO;
import com.source.loader.model3d.dto.Model3dPageDTO;
import com.source.loader.model3d.dto.Model3dShowcasePageDTO;
import com.source.loader.model3d.dto.Model3dUpdateDTO;
import com.source.loader.technical.*;
import com.source.loader.technical.file.strategy.BackgroundProcessing;
import com.source.loader.technical.file.strategy.HeightPolygonFileProcessing;
import com.source.loader.technical.file.strategy.LowPolygonFileProcessing;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class Model3DService {
    private final Model3DRepository model3DRepository;
    private final BrandService brandService;
    private final FileService fileService;


    public boolean createModel(Model3dCreatingDTO dto) {
        capitalizeMainVariables(dto);
        brandService.createNewBrand(dto.getBrand());
        Brand brand = brandService.findBrandByName(dto.getBrand());

        if (model3DRepository.existsByName(dto.getName()))
            return false;

        Model3D model3D = dto.toEntity(dto);
        model3D.setId(UUID.randomUUID());
        model3D.setBrand(brand);
        model3D = fileService.saveResources(model3D, dto);

        model3DRepository.updateModel3DSequenceWherePriorityMoreCurrent(model3D.getPriority(), model3D.getId());
        model3DRepository.save(model3D);
        return true;
    }

    public void updateModelPriorityById(String id, Long priority, Long lastPriority){
        UUID uuidId = UUID.fromString(id);
        Optional<Model3D> model3D = model3DRepository.findById(uuidId);
        if(model3D.isEmpty()){
            return;
        }
        model3DRepository.updateModel3DPriorityById(uuidId, priority);
        if(priority < lastPriority){
            model3DRepository.updateModel3DSequenceWherePriorityMoreCurrentAndLessLast(priority, lastPriority, uuidId);
        } else {
            model3DRepository.updateModel3DSequenceWherePriorityLessCurrentAndMoreLast(priority, lastPriority, uuidId);
        }
    }

    public Model3dCreatingDTO getLastModelPriority(){
        Optional<Model3D> model3D = model3DRepository.findFirstByOrderByPriorityDesc();
        if(model3D.isPresent()){
            return Model3dCreatingDTO.builder()
                    .priority(model3D.get().getPriority() + 1)
                    .build();
        }
        return new Model3dCreatingDTO();
    }

    @Modifying
    @Transactional
    public void removeModel(UUID id) {
        Model3D model3D = model3DRepository.findById(id).orElseThrow();
        fileService.removeModelResourcesById(model3D.getId());
        model3DRepository.delete(model3D);
        model3DRepository.updateModel3DSequenceWherePriorityLessCurrentAndMoreLast(
                model3DRepository.findFirstByOrderByPriorityDesc().get().getPriority(),
                model3D.getPriority(),
                id);
    }


    public Model3dUpdateDTO getModel3dUpdateDTO(UUID id) {
        Optional<Model3D> model3D = model3DRepository.findById(id);
        if (model3D.isEmpty())
            return null;

        Model3dUpdateDTO dto = new Model3dUpdateDTO();
        return dto.toDto(model3D.get());
    }

    public Model3D findModelByName(String name) {
        return model3DRepository.findByName(name).orElse(null);
    }

    public Page<Model3D> getTablePage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return model3DRepository.findAllByOrderByPriorityAsc(pageable);
    }

    public List<Model3dShowcasePageDTO> getModel3dCardList() {
        List<Model3D> result = model3DRepository.findTop12ByOrderByIdDesc();
        Model3dShowcasePageDTO dto = new Model3dShowcasePageDTO();
        return dto.toDtoList(result);
    }

    public Model3dPageDTO getModel3dPageById(UUID id) {
        Optional<Model3D> model3D = model3DRepository.findById(id);
        if (model3D.isPresent()) {
            Model3dPageDTO dto = new Model3dPageDTO();
            return dto.toDto(model3D.get());
        }
        return null;
    }

    public void updateModel3d(Model3dUpdateDTO dto) {
        capitalizeMainVariables(dto);
        Model3D model3D = dto.toEntity(dto);
        model3D.setBackgroundPath(fileService.updateFile(dto.getBackgroundPath(), dto.getCurrentBackgroundPath(), model3D.getId(), new BackgroundProcessing()));
        model3D.setHighPolygonPath(fileService.updateFile(dto.getHighPolygonPath(), dto.getCurrentHighPolygonPath(), model3D.getId(), new HeightPolygonFileProcessing()));
        model3D.setLowPolygonPath(fileService.updateFile(dto.getLowPolygonPath(), dto.getCurrentLowPolygonPath(), model3D.getId(), new LowPolygonFileProcessing()));
        model3D.setBrand(brandService.updateBrand(dto.getBrand()));
        model3DRepository.updateModel3DById(model3D.getId(), model3D.getName(), model3D.getDescription(),
                model3D.getLowPolygonPath(), model3D.getHighPolygonPath(), model3D.getBackgroundPath(),
                model3D.getBrand(), model3D.getPriority());
    }

    private void capitalizeMainVariables(@NotNull Model3dCreatingDTO dto) {
        dto.setBrand(capitalizeRecord(dto.getBrand()));
        dto.setName(capitalizeRecord(dto.getName()));
    }

    private void capitalizeMainVariables(Model3dUpdateDTO dto) {
        dto.setBrand(capitalizeRecord(dto.getBrand()));
        dto.setName(capitalizeRecord(dto.getName()));
    }

    private String capitalizeRecord(String name) {
        if (name != null && !name.isEmpty())
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        return name;
    }
}
