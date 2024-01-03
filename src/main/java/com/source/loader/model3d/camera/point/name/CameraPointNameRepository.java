package com.source.loader.model3d.camera.point.name;


import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CameraPointNameRepository extends JpaRepository<CameraPointName, Integer> {
    Optional<CameraPointName> findByName(String name);

    @Modifying
    @Query("UPDATE CameraPointName c SET c.name = :newName WHERE c.id = :id")
    @Transactional
    void updateCameraPointNameById(@Param("id") Integer id, @Param("newName") String newName);
}
