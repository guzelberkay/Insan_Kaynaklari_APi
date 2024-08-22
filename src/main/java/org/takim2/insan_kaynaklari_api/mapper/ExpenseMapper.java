package org.takim2.insan_kaynaklari_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.takim2.insan_kaynaklari_api.Vw.MyExpenseView;
import org.takim2.insan_kaynaklari_api.Vw.PendingExpenseView;
import org.takim2.insan_kaynaklari_api.dto.request.RegisterRequestDto;
import org.takim2.insan_kaynaklari_api.dto.response.ExpensesForApproveResponseDTO;
import org.takim2.insan_kaynaklari_api.dto.response.MyExpensesResponseDTO;
import org.takim2.insan_kaynaklari_api.dto.response.RegisterResponseDto;
import org.takim2.insan_kaynaklari_api.entity.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExpenseMapper {
    ExpenseMapper INSTANCE = Mappers.getMapper(ExpenseMapper.class);

    MyExpensesResponseDTO myExpenseViewToMyExpensesResponseDTO(MyExpenseView myExpenseView);
    ExpensesForApproveResponseDTO pendingExpenseViewToExpensesForApproveResponseDTO(PendingExpenseView pendingExpenseView);
}