package com.source.loader.model3d.camera.point.name;


import com.source.loader.model3d.Model3D;
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
@Table(name = "camera_point_names")
public class CameraPointName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", columnDefinition = "varchar(25) not null unique")
    private String name;

    @OneToMany(mappedBy = "cameraPointName", cascade = CascadeType.PERSIST)
    private Set<CameraPoint> views = new HashSet<>();
}
