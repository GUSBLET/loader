package com.source.loader.model3d.camera.point.color.description;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CameraPointColorDescriptionRepository extends JpaRepository<CameraPointColorDescription, Integer> {
    Optional<CameraPointColorDescription> findByName(String name);
}
