package com.source.loader.model3d;


import com.source.loader.brand.Brand;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

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
    @Column(columnDefinition = "BINARY(16) primary key")
    private UUID id;

    @Column(name = "name", columnDefinition = "varchar(50) not null unique")
    private String name;

    @Column(name = "description", columnDefinition = "varchar(500)")
    private String description;

    @Column(name = "low_polygon_path", columnDefinition = "text not null unique")
    private String lowPolygonPath;

    @Column(name = "high_polygon_path", columnDefinition = "text not null unique")
    private String highPolygonPath;

    @Column(name = "background_path", columnDefinition = "text unique")
    private String backgroundPath;

    @Column(name = "sequence", columnDefinition = "serial")
    private Long sequence;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_id")
    private Brand brand;
}
