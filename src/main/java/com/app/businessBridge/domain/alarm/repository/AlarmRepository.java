package com.app.businessBridge.domain.alarm.repository;

import com.app.businessBridge.domain.alarm.entity.Alarm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm,Long> {

    @Query("select A "
            + "from Alarm A "
            + "where "
            + "   (A.relationName = 'all' and A.relationId = 0) "
            + "   or (A.relationName = 'dept' and A.relationId = :deptId) "
            + "   or (A.relationName = 'member' and A.relationId = :memberId) "
            + "ORDER BY A.createdDate DESC "
            + "LIMIT 10")
    List<Alarm> findTop10(@Param("memberId") Long memberId, @Param("deptId")Long deptId);

    Page<Alarm> findByRelationNameAndRelationIdOrRelationNameAndRelationIdOrRelationNameAndRelationId(
            String relationName1, Long relationId1,
            String relationName2, Long relationId2,
            String relationName3, Long relationId3,
            Pageable pageable);

    
}
