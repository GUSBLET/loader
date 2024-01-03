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
public interface Model3DRepository extends PagingAndSortingRepository<Model3D, UUID> , JpaRepository<Model3D, UUID> {

    Optional<Model3D> findFirstByOrderByPriorityDesc();

    boolean existsByName(String name);

    List<Model3D> findTop12ByOrderByIdDesc();

    Page<Model3D> findAllByOrderByPriorityAsc(Pageable pageable);

    @Modifying
    @Query("UPDATE Model3D m SET m.priority = :priority WHERE m.id = :id")
    @Transactional
    void updateModel3DPriorityById(@Param("id") UUID id, @Param("priority") Long priority);

    @Modifying
    @Query("UPDATE Model3D m SET m.priority = m.priority + 1 WHERE m.priority >= :priority and m.id != :id")
    @Transactional
    void updateModel3DSequenceWherePriorityMoreCurrent(@Param("priority") Long priority, @Param("id") UUID id);

    @Modifying
    @Query("UPDATE Model3D m SET m.priority = m.priority + 1 WHERE m.priority >= :priority and m.priority <= :lastPriority and m.id != :id")
    @Transactional
    void updateModel3DSequenceWherePriorityMoreCurrentAndLessLast(@Param("priority") Long priority, @Param("lastPriority") Long lastPriority, @Param("id") UUID id);

    @Modifying
    @Query("UPDATE Model3D m SET m.priority = m.priority - 1 WHERE m.priority <= :priority and m.priority >= :lastPriority and m.id != :id")
    @Transactional
    void updateModel3DSequenceWherePriorityLessCurrentAndMoreLast(@Param("priority") Long priority, @Param("lastPriority") Long lastPriority, @Param("id") UUID id);

    @Modifying
    @Query("UPDATE Model3D m SET m.name = :name, m.description = :description, m.lowPolygonPath = :lowPolygonPath, " +
            "m.highPolygonPath = :highPolygonPath, m.backgroundPathLight = :backgroundPathLight, m.backgroundPathDark = :backgroundPathDark, " +
            "m.brand = :brand " +
            "WHERE m.id = :id")
    @Transactional
    void updateModel3DById(@Param("id") UUID id, @Param("name") String name, @Param("description") String description,
                           @Param("lowPolygonPath") String lowPolygonPath, @Param("highPolygonPath") String highPolygonPath,
                           @Param("backgroundPathLight") String backgroundPathLight, @Param("backgroundPathDark") String backgroundPathDark, @Param("brand") Brand brand);

    Optional<Model3D> findByName(String name);
}
