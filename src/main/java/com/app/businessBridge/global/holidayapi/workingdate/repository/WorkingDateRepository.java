package com.app.businessBridge.global.holidayapi.workingdate.repository;

import com.app.businessBridge.domain.rebate.entity.Rebate;
import com.app.businessBridge.global.holidayapi.workingdate.entity.WorkingDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkingDateRepository extends JpaRepository<WorkingDate, Long> {

    @Query("select " +
            "distinct wd " +
            "from WorkingDate wd " +
            "where wd.year = :year " +
            "and wd.month = :month ")
    WorkingDate findByYearAndMonth(@Param("year") String year, @Param("month") String month);

}
