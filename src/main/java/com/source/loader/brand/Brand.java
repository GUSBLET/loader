package com.source.loader.brand;

import com.source.loader.model3d.Model3D;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "brands")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", columnDefinition = "varchar(50) not null unique")
    private String name;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.PERSIST)
    private Set<Model3D> views = new HashSet<>();
}
