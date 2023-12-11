package com.source.loader.model3d;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Model3DRepository extends JpaRepository<Model3D, Long> {
    boolean existsByName(String name);

    List<Model3D> findTop12ByOrderByIdDesc();
}
