package com.source.loader.model3d;


import com.source.loader.brand.Brand;
import com.source.loader.model3d.camera.point.CameraPoint;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "models")
public class Model3D {

    @Id
    @Column(columnDefinition = "uuid primary key")
    private UUID id;

    @Column(name = "name", columnDefinition = "varchar(50) not null unique")
    private String name;

    @Column(name = "description", columnDefinition = "varchar(500)")
    private String description;

    @Column(name = "low_polygon_path", columnDefinition = "text not null unique")
    private String lowPolygonPath;

    @Column(name = "high_polygon_path", columnDefinition = "text not null unique")
    private String highPolygonPath;

    @Column(name = "background_path_light", columnDefinition = "text unique")
    private String backgroundPathLight;

    @Column(name = "background_path_dark", columnDefinition = "text unique")
    private String backgroundPathDark;

    @Column(name = "priority", columnDefinition = "serial")
    private Long priority;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @OneToMany(mappedBy = "model3D", cascade = CascadeType.ALL , fetch = FetchType.EAGER,  orphanRemoval = true)
    private Set<CameraPoint> cameraPoints = new HashSet<>();
}
