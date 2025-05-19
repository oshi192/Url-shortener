package com.example.clickcollector.repository;

import com.example.clickcollector.model.ClickEvent;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClickEventRepo extends JpaRepository<ClickEvent, Long> {
    @Query("SELECT COUNT(c) FROM ClickEvent c WHERE c.ts > :since")
    long countSince(@Param("since") long since);

    @Query("SELECT c.geo AS region, COUNT(c) AS cnt FROM ClickEvent c GROUP BY c.geo")
    List<GeoCount> byRegion();

    @Query("""
            SELECT c.alias AS alias, COUNT(c) AS cnt
              FROM ClickEvent c
             GROUP BY c.alias
             ORDER BY COUNT(c) DESC
            """)
    List<AliasCount> findTopUrls(Pageable page);
}
