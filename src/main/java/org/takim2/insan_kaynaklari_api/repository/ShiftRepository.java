package org.takim2.insan_kaynaklari_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.takim2.insan_kaynaklari_api.Vw.MyShiftsView;
import org.takim2.insan_kaynaklari_api.entity.Employee;
import org.takim2.insan_kaynaklari_api.entity.Shift;

import java.util.List;

@Repository
public interface ShiftRepository  extends JpaRepository<Shift, Long> {
   // @Query("select new org.takim2.insan_kaynaklari_api.Vw.MyShiftsView(e.startDate,e.endDate,e.startTime,e.endTime) from Shift  e where e.employee =?1")

    @Query("select new org.takim2.insan_kaynaklari_api.Vw.MyShiftsView(s.startDate, s.endDate, s.startTime, s.endTime) " +
            "from Shift s join s.employee e where e = :employee")
    List<MyShiftsView> findAllMyShiftViewByEmployee(Employee employee);
}
