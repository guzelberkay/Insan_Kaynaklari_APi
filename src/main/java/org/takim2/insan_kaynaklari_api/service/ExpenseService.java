package org.takim2.insan_kaynaklari_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.takim2.insan_kaynaklari_api.Vw.MyExpenseView;
import org.takim2.insan_kaynaklari_api.Vw.PendingExpenseView;
import org.takim2.insan_kaynaklari_api.dto.request.ExpenseSaveDTO;
import org.takim2.insan_kaynaklari_api.dto.request.UpdateExpenseRequestDTO;
import org.takim2.insan_kaynaklari_api.dto.response.ExpensesForApproveResponseDTO;
import org.takim2.insan_kaynaklari_api.dto.response.MyExpensesResponseDTO;
import org.takim2.insan_kaynaklari_api.entity.Company;
import org.takim2.insan_kaynaklari_api.entity.Employee;
import org.takim2.insan_kaynaklari_api.entity.Expense;
import org.takim2.insan_kaynaklari_api.entity.enums.ExpenseStatus;
import org.takim2.insan_kaynaklari_api.exception.ErrorType;
import org.takim2.insan_kaynaklari_api.exception.HumanResourcesAppException;
import org.takim2.insan_kaynaklari_api.mapper.ExpenseMapper;
import org.takim2.insan_kaynaklari_api.repository.ExpenseRepository;
import org.takim2.insan_kaynaklari_api.util.JwtTokenManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final JwtTokenManager jwtTokenManager;
    private final EmployeeService employeeService;
    private final CompanyService companyService;


    public void saveExpense(ExpenseSaveDTO expenseSaveDTO) {
        Long userId = jwtTokenManager.getUserIdFromToken(expenseSaveDTO.getToken()).orElseThrow(() -> new HumanResourcesAppException(ErrorType.USER_NOT_FOUND));
        Employee employee = employeeService.findEmployeeByUserId(userId);

        expenseRepository.save(Expense.builder()
                        .expenseDate(expenseSaveDTO.getExpenseDate())
                        .amount(expenseSaveDTO.getAmount())
                        .employee(employee)
                        .description(expenseSaveDTO.getDescription())
                        .company(employee.getCompany())
                .build());


    }

    public List<MyExpensesResponseDTO> getMyExpenses(String jwtToken) {
        Long userId = jwtTokenManager.getUserIdFromToken(jwtToken).orElseThrow(() -> new HumanResourcesAppException(ErrorType.USER_NOT_FOUND));
        Employee employee = employeeService.findEmployeeByUserId(userId);

        List<MyExpensesResponseDTO> myExpensesResponseDTOList = new ArrayList<>();

        List<MyExpenseView> myExpenseViewByEmployee = expenseRepository.findAllMyExpenseViewByEmployee(employee);
        if(myExpenseViewByEmployee.isEmpty()){
            throw new HumanResourcesAppException(ErrorType.DATA_NOT_FOUND);
        }

        myExpenseViewByEmployee.forEach(expenseView -> {
            myExpensesResponseDTOList.add(ExpenseMapper.INSTANCE.myExpenseViewToMyExpensesResponseDTO(expenseView));
        });


        return myExpensesResponseDTOList;
    }

    public List<ExpensesForApproveResponseDTO> getPendingExpenses(Long companyId) {
        Company company = companyService.findCompanyById(companyId);
        List<PendingExpenseView> allPendingExpensesByCompany = expenseRepository.findAllPendingExpensesBuCompany(company,ExpenseStatus.PENDING);
        if(allPendingExpensesByCompany.isEmpty()){
            throw new HumanResourcesAppException(ErrorType.DATA_NOT_FOUND);
        }
        List<ExpensesForApproveResponseDTO> allPendingExpensesResponseDTOList = new ArrayList<>();
        allPendingExpensesByCompany.forEach(expenseView -> {
            allPendingExpensesResponseDTOList.add(ExpenseMapper.INSTANCE.pendingExpenseViewToExpensesForApproveResponseDTO(expenseView));
        });
        return allPendingExpensesResponseDTOList;
    }

    public void updateExpenseStatus(UpdateExpenseRequestDTO updateExpenseRequestDTO) {
        Expense expense = expenseRepository.findById(updateExpenseRequestDTO.getExpenseId()).orElseThrow(() -> new HumanResourcesAppException(ErrorType.DATA_NOT_FOUND));
        if(updateExpenseRequestDTO.getIsApproved()){
            expense.setExpenseStatus(ExpenseStatus.APPROVED);
            Employee employee = employeeService.findEmployeeById(expense.getEmployee().getId());
            employee.setSalary(employee.getSalary().add(expense.getAmount()));
            employeeService.saveEmployee(employee);
        } else {
            expense.setExpenseStatus(ExpenseStatus.REJECTED);
        }
        expenseRepository.save(expense);
    }
}
