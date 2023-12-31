package com.source.loader.model3d.camera.point;

import com.source.loader.model3d.camera.point.name.CameraPointNameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CameraPointService {
    private final CameraPointRepository cameraPointRepository;
    private final CameraPointNameService cameraPointNameService;
}
