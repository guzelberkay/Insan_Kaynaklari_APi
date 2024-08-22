package org.takim2.insan_kaynaklari_api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ShiftRequestDto {
    private Long employeeId;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
}