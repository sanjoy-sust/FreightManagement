package com.fm.assignment.core.dao;

import com.fm.assignment.core.entity.PlaceEntity;
import com.vividsolutions.jts.geom.Geometry;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Sanjoy Kumer Deb
 * @since 10/10/2017.
 */
@Repository
@Transactional(readOnly = true)
public interface PlaceRepository extends JpaRepository<PlaceEntity, Long> {
    PlaceEntity findByCode(String code);
    PlaceEntity findByName(String name);
    //@TODO need to move JPQL.
    @Query(value = "select p.* from (SELECT a.*,\n" +
            "   111.111 *\n" +
            "    DEGREES(ACOS(COS(RADIANS(a.Latitude))\n" +
            "         * COS(RADIANS(:latitude))\n" +
            "         * COS(RADIANS(a.Longitude - :longitude))\n" +
            "         + SIN(RADIANS(a.Latitude))\n" +
            "         * SIN(RADIANS(:latitude)))) AS distance_in_km\n" +
            "  FROM place AS a HAVING distance_in_km < :distance \n" +
            "    ORDER BY distance_in_km ASC) b\n" +
            "    inner join \n" +
            "    place as p\n" +
            "    on b.id = p.id",nativeQuery = true)
    @Cacheable("findLocationWithin")
    List<PlaceEntity> findLocationWithin(@Param("latitude")Double latitude,@Param("longitude")Double longitude,@Param("distance") double distance);
}

