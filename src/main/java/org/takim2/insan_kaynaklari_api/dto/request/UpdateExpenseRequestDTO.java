package org.takim2.insan_kaynaklari_api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UpdateExpenseRequestDTO {
    private Long expenseId;
    private Boolean isApproved;
}
