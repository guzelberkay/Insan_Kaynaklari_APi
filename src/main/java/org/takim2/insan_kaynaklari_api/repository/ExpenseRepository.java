package org.takim2.insan_kaynaklari_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.takim2.insan_kaynaklari_api.Vw.MyExpenseView;
import org.takim2.insan_kaynaklari_api.Vw.PendingExpenseView;
import org.takim2.insan_kaynaklari_api.entity.Company;
import org.takim2.insan_kaynaklari_api.entity.Employee;
import org.takim2.insan_kaynaklari_api.entity.Expense;
import org.takim2.insan_kaynaklari_api.entity.enums.ExpenseStatus;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Query("select new org.takim2.insan_kaynaklari_api.Vw.MyExpenseView(e.amount,e.description,e.expenseDate,e.expenseStatus) from Expense  e where e.employee =?1")
    List<MyExpenseView> findAllMyExpenseViewByEmployee(Employee employee);


    @Query("select new org.takim2.insan_kaynaklari_api.Vw.PendingExpenseView(e.id,e.amount,e.expenseDate,e.description,e.expenseStatus,e.employee.user.firstName,e.employee.user.lastName) from Expense e where e.company=?1 and e.expenseStatus=?2")
    List<PendingExpenseView> findAllPendingExpensesBuCompany(Company company, ExpenseStatus expenseStatus);


}
