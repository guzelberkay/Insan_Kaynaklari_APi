package org.takim2.insan_kaynaklari_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.takim2.insan_kaynaklari_api.entity.CompanyManager;

import java.util.Optional;

@Repository
public interface CompanyManagerRepository extends JpaRepository<CompanyManager, Long> {
    Optional<CompanyManager> findByUserId(Long userId);

}
