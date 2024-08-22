package org.takim2.insan_kaynaklari_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.takim2.insan_kaynaklari_api.entity.enums.ExpenseStatus;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ExpensesForApproveResponseDTO {
    private Long expenseId;
    private BigDecimal amount;
    private Long expenseDate;
    private String description;
    private ExpenseStatus expenseStatus;

    private String firstName;
    private String lastName;

}
