package org.takim2.insan_kaynaklari_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.takim2.insan_kaynaklari_api.entity.enums.SubscriptionPlan;
import org.takim2.insan_kaynaklari_api.entity.enums.UserRole;
import org.takim2.insan_kaynaklari_api.entity.enums.UserStatus;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PendingCompaniesResponseDTO {
    //Company manager bilgileri
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private UserStatus userStatus;
    private UserRole userRole;


    //Company manager'a ait şirketin bilgileri (onay sürecinde bakılmak istenebileceğini düşündüm.)
    private Long companyId;
    private String companyName;
    private Integer numberOfEmployees;
    private SubscriptionPlan subscriptionPlan;
}
