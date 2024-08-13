package org.takim2.insan_kaynaklari_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseDto {
    private Long user;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String hireDate;
    private String birthDate;
    private int annualLeave;
    private boolean isActive;
}
