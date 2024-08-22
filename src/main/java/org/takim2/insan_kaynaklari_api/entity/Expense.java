package org.takim2.insan_kaynaklari_api.entity;

import jakarta.persistence.*;
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
@Entity
@Table(name = "tbl_expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Employee employee;
    @ManyToOne
    private Company company;
    private BigDecimal amount;
    private Long expenseDate;
    private String description;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private ExpenseStatus expenseStatus = ExpenseStatus.PENDING;

    @Builder.Default
    private Long createAt=System.currentTimeMillis();
    private Long updateAt;
}
