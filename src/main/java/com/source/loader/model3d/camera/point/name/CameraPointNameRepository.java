package com.source.loader.model3d.camera.point.name;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CameraPointNameRepository extends JpaRepository<CameraPointName, Integer> {
}
