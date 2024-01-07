package com.source.loader.model3d;

import com.source.loader.brand.Brand;
import com.source.loader.brand.BrandService;
import com.source.loader.model3d.camera.point.dto.CameraPointDTO;
import com.source.loader.model3d.camera.point.CameraPointService;
import com.source.loader.model3d.dto.Model3dCreatingDTO;
import com.source.loader.model3d.dto.Model3dPageDTO;
import com.source.loader.model3d.dto.Model3dShowcasePageDTO;
import com.source.loader.model3d.dto.Model3dUpdateDTO;
import com.source.loader.technical.*;
import com.source.loader.technical.file.strategy.BackgroundProcessing;
import com.source.loader.technical.file.strategy.HeightPolygonFileProcessing;
import com.source.loader.technical.file.strategy.LowPolygonFileProcessing;
import com.source.loader.technical.unique.string.customizer.UniqueStringCustomizer;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class Model3DService {
    private final Model3DRepository model3DRepository;
    private final BrandService brandService;
    private final FileService fileService;
    private final CameraPointService cameraPointService;


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
        model3D = model3DRepository.save(model3D);
        model3D.setCameraPoints(cameraPointService.createCameraPoints(govno(model3D)));
        return true;
    }



    public void updateModelPriorityById(String id, Long priority, Long lastPriority) {
        UUID uuidId = UUID.fromString(id);
        Optional<Model3D> model3D = model3DRepository.findById(uuidId);
        if (model3D.isEmpty()) {
            return;
        }
        model3DRepository.updateModel3DPriorityById(uuidId, priority);
        if (priority < lastPriority) {
            model3DRepository.updateModel3DSequenceWherePriorityMoreCurrentAndLessLast(priority, lastPriority, uuidId);
        } else {
            model3DRepository.updateModel3DSequenceWherePriorityLessCurrentAndMoreLast(priority, lastPriority, uuidId);
        }
    }

    public Model3dCreatingDTO getLastModelPriority() {
        Optional<Model3D> model3D = model3DRepository.findFirstByOrderByPriorityDesc();
        if (model3D.isPresent()) {
            return Model3dCreatingDTO.builder()
                    .priority(model3D.get().getPriority() + 1)
                    .build();
        }
        return Model3dCreatingDTO.builder()
                .priority(1L)
                .build();
    }


    public void removeModel(UUID id) {
        Optional<Model3D> model3D = model3DRepository.findById(id);
        if (model3D.isEmpty()) {
            return;
        }
        fileService.removeModelResourcesById(model3D.get().getId());

        model3DRepository.delete(model3D.get());

        if(model3DRepository.count() > 0){
            model3DRepository.updateModel3DSequenceWherePriorityLessCurrentAndMoreLast(
                    model3DRepository.findFirstByOrderByPriorityDesc().get().getPriority(),
                    model3D.get().getPriority(),
                    id);
        }
    }


    public Model3dUpdateDTO getModel3dUpdateDTO(UUID id) {
        Optional<Model3D> model3D = model3DRepository.findById(id);
        if (model3D.isEmpty())
            return null;

        Model3dUpdateDTO dto = new Model3dUpdateDTO();
        return dto.toDto(model3D.get());
    }

    public Model3D findModelByName(String name) {
        return model3DRepository.findByName(UniqueStringCustomizer.capitalizeRecord(name)).orElse(null);
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
        model3D.setBackgroundPathLight(fileService.updateFile(dto.getBackgroundPathLight(), dto.getCurrentBackgroundPathLight(), model3D.getId().toString(), new BackgroundProcessing(fileService.ABSOLUTE_PATH)));
        model3D.setBackgroundPathDark(fileService.updateFile(dto.getBackgroundPathDark(), dto.getCurrentBackgroundPathDark(), model3D.getId().toString(), new BackgroundProcessing(fileService.ABSOLUTE_PATH)));
        model3D.setHighPolygonPath(fileService.updateFile(dto.getHighPolygonPath(), dto.getCurrentHighPolygonPath(), model3D.getId().toString(), new HeightPolygonFileProcessing(fileService.ABSOLUTE_PATH)));
        model3D.setLowPolygonPath(fileService.updateFile(dto.getLowPolygonPath(), dto.getCurrentLowPolygonPath(), model3D.getId().toString(), new LowPolygonFileProcessing(fileService.ABSOLUTE_PATH)));
        model3D.setBrand(brandService.updateBrand(dto.getBrand()));
        model3DRepository.updateModel3DById(model3D.getId(), model3D.getName(), model3D.getDescription(),
                model3D.getLowPolygonPath(), model3D.getHighPolygonPath(), model3D.getBackgroundPathLight(),
                model3D.getBackgroundPathDark(), model3D.getBrand());
    }

    private void capitalizeMainVariables(@NotNull Model3dCreatingDTO dto) {
        dto.setBrand(UniqueStringCustomizer.capitalizeRecord(dto.getBrand()));
        dto.setName(UniqueStringCustomizer.capitalizeRecord(dto.getName()));
    }

    private void capitalizeMainVariables(Model3dUpdateDTO dto) {
        dto.setBrand(UniqueStringCustomizer.capitalizeRecord(dto.getBrand()));
        dto.setName(UniqueStringCustomizer.capitalizeRecord(dto.getName()));
    }


    private List<CameraPointDTO> govno(Model3D model3D) {
        List<CameraPointDTO> list = new ArrayList<>();
        list.add(CameraPointDTO.builder()
                .point_x_position(-0.146)
                .point_y_position(0.0)
                .point_z_position(0.0)
                .camera_x_position(-1.5)
                .camera_y_position(1.6)
                .camera_z_position(0.8)
                .name("top")
                .colorDescription("black")
                .model3D(model3D)
                .build());

        list.add(CameraPointDTO.builder()
                .point_x_position(-0.01)
                .point_y_position(0.0)
                .point_z_position(0.0)
                .camera_x_position(1.2)
                .camera_y_position(-2.5)
                .camera_z_position(-0.9)
                .name("middle")
                .colorDescription("black")
                .model3D(model3D)
                .build());

        list.add(CameraPointDTO.builder()
                .point_x_position(0.1)
                .point_y_position(0.0)
                .point_z_position(0.0)
                .camera_x_position(2.0)
                .camera_y_position(0.0)
                .camera_z_position(0.8)
                .name("bottom")
                .colorDescription("black")
                .model3D(model3D)
                .build());

        return list;
    }

}
