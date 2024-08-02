package org.takim2.insan_kaynaklari_api.Vw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.takim2.insan_kaynaklari_api.entity.enums.SubscriptionPlan;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PassiveCompaniesView {
    private Long id; //CompanyId
    private Long companyManagersUserId;
    private String companyName;
    private Integer numberOfEmployees;
    private SubscriptionPlan subscriptionPlan;

}
