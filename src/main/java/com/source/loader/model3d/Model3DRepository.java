package com.source.loader.model3d;

import com.source.loader.brand.Brand;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface Model3DRepository extends PagingAndSortingRepository<Model3D, UUID> ,JpaRepository<Model3D, UUID> {
    boolean existsByName(String name);

    List<Model3D> findTop12ByOrderByIdDesc();

    Page<Model3D> findAll(Pageable pageable);

    @Modifying
    @Query("UPDATE Model3D m SET m.name = :name, m.description = :description, m.lowPolygonPath = :lowPolygonPath, " +
            "m.heightPolygonPath = :heightPolygonPath, m.backgroundPath = :backgroundPath, m.brand = :brand " +
            "WHERE m.id = :id")
    @Transactional
    void updateModel3DById(@Param("id") UUID id, @Param("name") String name, @Param("description") String description,
                           @Param("lowPolygonPath") String lowPolygonPath, @Param("heightPolygonPath") String heightPolygonPath,
                           @Param("backgroundPath") String backgroundPath, @Param("brand") Brand brand);

    Optional<Model3D> findByNameAndId(String name, UUID id);

    Optional<Model3D> findByName(String name);
}
