package org.takim2.insan_kaynaklari_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import org.takim2.insan_kaynaklari_api.dto.response.EmployeeResponseDto;
import org.takim2.insan_kaynaklari_api.entity.Employee;
import org.takim2.insan_kaynaklari_api.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.takim2.insan_kaynaklari_api.Vw.EmployeeView;
import org.takim2.insan_kaynaklari_api.entity.Employee;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByUser(User user);

    @Query("select new org.takim2.insan_kaynaklari_api.Vw.EmployeeView(e.id,e.user.firstName,e.user.lastName,e.annualLeave) from Employee e where e.company.id=?1")
    List<EmployeeView> findAllBycompanyId(Long companyId);

    @Query("select e from Employee e where e.company.id in ?1")
    List<Employee> findAllByCompanyIdIn(List<Long> companyIds);
}





