package org.takim2.insan_kaynaklari_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.takim2.insan_kaynaklari_api.entity.Asset;

import java.util.List;

public interface AssetRepository extends JpaRepository<Asset, Long> {

    List<Asset> findAllByUserId(Long userId);

    @Query("SELECT a FROM Asset a WHERE a.user.id IN (SELECT e.user.id FROM Employee e WHERE e.company.id = ?1)")
    List<Asset> findAssetsByCompanyId(Long companyId);
}
