package org.takim2.insan_kaynaklari_api.Vw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class EmployeeView {
    private Long employeeId;
    private String employeeName;
    private String employeeSurname;
    private Integer annualLeave;
}
