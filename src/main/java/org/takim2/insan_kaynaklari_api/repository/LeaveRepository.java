package org.takim2.insan_kaynaklari_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.takim2.insan_kaynaklari_api.Vw.PendingLeaveView;
import org.takim2.insan_kaynaklari_api.entity.Leave;

import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {
    List<PendingLeaveView> findAllPendingLeaves(Long com); // Burayı düşünmem lazım karmaşık olabilir.
}
