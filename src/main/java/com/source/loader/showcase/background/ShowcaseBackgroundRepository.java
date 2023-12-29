package com.source.loader.showcase.background;


import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ShowcaseBackgroundRepository extends PagingAndSortingRepository<ShowcaseBackground, UUID>, JpaRepository<ShowcaseBackground, UUID> {
    boolean existsByModeName(String name);

    @Modifying
    @Query("UPDATE ShowcaseBackground s SET s.modeName = :modeName, s.name = :name " +
            "WHERE s.id = :id")
    @Transactional
    void updateShowcaseBackgroundById(@Param("id") UUID id, @Param("modeName") String modeName, @Param("name") String name);

    Optional<ShowcaseBackground> findByModeName(String modeName);
}
