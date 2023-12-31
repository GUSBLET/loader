package com.source.loader.model3d.camera.point;

import com.source.loader.brand.Brand;
import com.source.loader.model3d.Model3D;
import com.source.loader.model3d.camera.point.color.description.CameraPointColorDescription;
import com.source.loader.model3d.camera.point.name.CameraPointName;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "camera_points")
public class CameraPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "description", columnDefinition = "varchar(500)")
    private String description;

    @Column(name = "point_x_position", columnDefinition = "double precision")
    private Double point_x_position;

    @Column(name = "point_y_position", columnDefinition = "double precision")
    private Double point_y_position;

    @Column(name = "point_z_position", columnDefinition = "double precision")
    private Double point_z_position;

    @Column(name = "camera_x_position", columnDefinition = "double precision")
    private Double camera_x_position;

    @Column(name = "camera_y_position", columnDefinition = "double precision")
    private Double camera_y_position;

    @Column(name = "camera_z_position", columnDefinition = "double precision")
    private Double camera_z_position;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "model_id")
    private Model3D model3D;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "camera_point_name_id")
    private CameraPointName cameraPointName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "camera_point_color_description_id")
    private CameraPointColorDescription cameraPointColorDescription;
}
