package org.takim2.insan_kaynaklari_api.Vw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.takim2.insan_kaynaklari_api.entity.enums.UserRole;
import org.takim2.insan_kaynaklari_api.entity.enums.UserStatus;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserView {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private UserStatus userStatus;
    private UserRole userRole;
}
