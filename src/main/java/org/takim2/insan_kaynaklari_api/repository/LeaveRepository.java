package org.takim2.insan_kaynaklari_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.takim2.insan_kaynaklari_api.Vw.PendingLeaveView;
import org.takim2.insan_kaynaklari_api.entity.Leave;
import org.takim2.insan_kaynaklari_api.entity.enums.LeaveStatus;

import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {

    @Query("select new org.takim2.insan_kaynaklari_api.Vw.PendingLeaveView(l.id,l.employee.id,l.employee.user.firstName,l.employee.user.lastName,l.leaveType,l.startDate,l.endDate,l.leaveStatus,l.description) from Leave l where l.employee.company.id=?1 and l.leaveStatus=?2")
    List<PendingLeaveView> findAllPendingLeavesByCompanyId(Long companyId, LeaveStatus leaveStatus);

}
