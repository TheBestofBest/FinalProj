package com.app.businessBridge.domain.rebate.repository;

import com.app.businessBridge.domain.rebate.entity.Rebate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RebateRepository extends JpaRepository<Rebate, Long> {

    @Query("select " +
            "distinct r " +
            "from Rebate r " +
            "where r.member.id = :id " +
            " order by r.createdDate desc")
    List<Rebate> findMyRebates(@Param("id") String id);


    @Query("select " +
            "distinct r " +
            "from Rebate r " +
            "where r.year = :year " +
            "and r.month = :month " +
            " order by r.createdDate desc")
    List<Rebate> findByYearAndMonth(@Param("year") String year, @Param("month") String month);
}
