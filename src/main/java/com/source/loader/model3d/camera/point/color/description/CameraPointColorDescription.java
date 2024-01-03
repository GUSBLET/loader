package com.source.loader.model3d.camera.point.color.description;


import com.source.loader.model3d.camera.point.CameraPoint;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "camera_point_color_description")
public class CameraPointColorDescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", columnDefinition = "varchar(30) not null unique")
    private String name;

    @OneToMany(mappedBy = "cameraPointColorDescription", cascade = CascadeType.PERSIST)
    private Set<CameraPoint> cameraPoints = new HashSet<>();
}
