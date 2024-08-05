package org.takim2.insan_kaynaklari_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.takim2.insan_kaynaklari_api.Vw.PassiveCompaniesView;
import org.takim2.insan_kaynaklari_api.entity.Company;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Query("select new org.takim2.insan_kaynaklari_api.Vw.PassiveCompaniesView (c.id,c.companyManager.user.id,c.companyName,c.employeeNumberLimit,c.subscriptionPlan) from Company  c where c.isActive=?1")
    List<PassiveCompaniesView> findByIsActive(boolean isActive);
}
