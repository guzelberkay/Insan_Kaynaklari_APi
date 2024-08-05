package org.takim2.insan_kaynaklari_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tbl_company_managers")
public class CompanyManager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User user;

    @Builder.Default
    private boolean isActive = false;
    @Builder.Default
    private Long createAt=System.currentTimeMillis();
    private Long updateAt;
}
