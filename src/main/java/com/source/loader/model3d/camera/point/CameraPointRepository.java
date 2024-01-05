package com.source.loader.model3d.camera.point;

import com.source.loader.model3d.camera.point.color.description.CameraPointColorDescription;
import com.source.loader.model3d.camera.point.name.CameraPointName;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface CameraPointRepository extends JpaRepository<CameraPoint, UUID> {

    @Modifying
    @Query("UPDATE CameraPoint c SET c.cameraPointColorDescription = :colorDescription WHERE c.id = :id")
    @Transactional
    void updateCameraPointColorDescriptionById(@Param("id") UUID id, @Param("colorDescription")CameraPointColorDescription colorDescription);

    @Modifying
    @Query("UPDATE CameraPoint c SET c.camera_x_position = :camera_x_position, c.camera_y_position = :camera_y_position, " +
            "c.camera_z_position = :camera_z_position, c.point_x_position = :point_x_position, " +
            "c.point_y_position = :point_y_position, c.point_z_position = :point_z_position," +
            "c.description = :description, c.cameraPointName = :cameraPointName " +
            "WHERE c.id = :id")
    @Transactional
    void updateCameraPointWithoutColorDescriptionById(@Param("id") UUID id, @Param("cameraPointName") CameraPointName cameraPointName,
                                                      @Param("description") String description, @Param("point_x_position") float point_x_position,
                                                      @Param("point_y_position") float point_y_position, @Param("point_z_position") float point_z_position,
                                                      @Param("camera_x_position") float camera_x_position, @Param("camera_y_position") float camera_y_position,
                                                      @Param("camera_z_position") float camera_z_position);

    @Modifying
    @Query("UPDATE CameraPoint c SET c.camera_x_position = :camera_x_position, c.camera_y_position = :camera_y_position, " +
            "c.camera_z_position = :camera_z_position " +
            "WHERE c.id = :id")
    @Transactional
    void updateCameraPositionById(@Param("id") UUID id, @Param("camera_x_position") float camera_x_position,
                                                      @Param("camera_y_position") float camera_y_position, @Param("camera_z_position") float camera_z_position);



    @Modifying
    @Query("DELETE FROM CameraPoint c WHERE c.model3D.id = :model3DId")
    void deleteByModel3DId(@Param("model3DId") UUID model3DId);
}
