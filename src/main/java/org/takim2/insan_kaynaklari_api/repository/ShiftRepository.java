package org.takim2.insan_kaynaklari_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.takim2.insan_kaynaklari_api.entity.Shift;

@Repository
public interface ShiftRepository  extends JpaRepository<Shift, Long> {
}
