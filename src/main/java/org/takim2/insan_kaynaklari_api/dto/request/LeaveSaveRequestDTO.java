package org.takim2.insan_kaynaklari_api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.takim2.insan_kaynaklari_api.entity.enums.LeaveType;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LeaveSaveRequestDTO {
    private Long employeeId;
    private String description;
    private LeaveType leaveType;
    private Long startDate;
    private Long endDate;
}
