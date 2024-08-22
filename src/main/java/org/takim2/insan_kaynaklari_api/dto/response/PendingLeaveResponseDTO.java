package org.takim2.insan_kaynaklari_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.takim2.insan_kaynaklari_api.entity.enums.LeaveStatus;
import org.takim2.insan_kaynaklari_api.entity.enums.LeaveType;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PendingLeaveResponseDTO {
    private Long leaveId;
    private Long employeeId;
    private String employeeName;
    private String employeeSurname;
    private LeaveType leaveType;
    private Long startDate;
    private Long endDate;
    private LeaveStatus leaveStatus;
    private String description;
}
