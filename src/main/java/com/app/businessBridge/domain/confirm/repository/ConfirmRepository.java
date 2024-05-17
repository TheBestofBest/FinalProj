package com.app.businessBridge.domain.confirm.repository;

import com.app.businessBridge.domain.confirm.entity.Confirm;
import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.domain.rebate.entity.Rebate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfirmRepository extends JpaRepository<Confirm, Long> {

//    @Query("SELECT c " +
//            "FROM Confirm c " +
//            "WHERE c.confirmRequestMemberId = :confirmRequestMemberId " +
//            "AND FUNCTION('DATE_FORMAT', c.createdDate, '%Y-%m') = :year-:month")
//    Confirm findVacation(@Param("confirm_request_member_id") Member member, @Param("year") String year,@Param("month") String month);

}
