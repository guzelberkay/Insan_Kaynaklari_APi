package org.takim2.insan_kaynaklari_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.takim2.insan_kaynaklari_api.entity.enums.EmployeeLimitLevel;
import org.takim2.insan_kaynaklari_api.entity.enums.SubscriptionPlan;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tbl_companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;

    @Builder.Default
    private Integer numberOfEmployees=0;
    @Enumerated(EnumType.STRING)
    private EmployeeLimitLevel employeeLimitLevel;
    private Integer employeeNumberLimit;
    @ManyToOne
    private CompanyManager companyManager;
    @Enumerated(EnumType.STRING)
    private SubscriptionPlan subscriptionPlan;
    private boolean isActive;
    @Builder.Default
    private Long createAt=System.currentTimeMillis();
    private Long updateAt;

}
