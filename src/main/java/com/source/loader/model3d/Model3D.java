package com.source.loader.model3d;


import com.source.loader.brand.Brand;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "models")
public class Model3D {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", columnDefinition = "varchar(50) not null unique")
    private String name;

    @Column(name = "description", columnDefinition = "varchar(500)")
    private String description;

    @Column(name = "low_polygon_path", columnDefinition = "text not null unique")
    private String lowPolygonPath;

    @Column(name = "height_polygon_path", columnDefinition = "text not null unique")
    private String heightPolygonPath;

    @Column(name = "background_path", columnDefinition = "text unique")
    private String backgroundPath;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_id")
    private Brand brand;
}
