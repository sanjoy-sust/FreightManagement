package com.fm.assignment.core.dao;

import com.fm.assignment.core.entity.PathEntity;
import com.fm.assignment.core.enums.TransportTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Sanjoy Kumer Deb
 * @since 06/10/2017.
 */
@Repository
@Transactional(readOnly = true)
public interface PathRepository extends JpaRepository<PathEntity, Long> {
    List<PathEntity> findByRouteType(TransportTypeEnum routeType);
    List<PathEntity> findByRouteTypeAndContainerSize(TransportTypeEnum routeType,long containerSize);
    List<PathEntity> findByContainerSize(long containerSize);
}
