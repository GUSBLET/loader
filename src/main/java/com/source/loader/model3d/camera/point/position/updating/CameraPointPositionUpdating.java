package com.source.loader.model3d.camera.point.position.updating;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CameraPointPositionUpdating {

    private LocalTime timestamp;
    private String secretKey;
}
