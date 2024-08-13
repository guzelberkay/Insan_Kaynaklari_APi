package org.takim2.insan_kaynaklari_api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class EmployeeRequestDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String hireDate;
    private String birthDate;
    private Integer annualLeave;
    private boolean isActive;
}