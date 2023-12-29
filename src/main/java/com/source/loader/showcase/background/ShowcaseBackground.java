package com.source.loader.showcase.background;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "showcase_backgrounds")
public class ShowcaseBackground {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", columnDefinition = "text not null unique")
    private String name;

    @Column(name = "mode_name", columnDefinition = "varchar(50) not null unique")
    private String modeName;
}
