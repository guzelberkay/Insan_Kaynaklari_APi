package org.takim2.insan_kaynaklari_api.entity.enums;

public enum EmployeeLimitLevel {
    EMPLOYEE_LIMIT_LEVEL_1(60),
    EMPLOYEE_LIMIT_LEVEL_2(150),
    EMPLOYEE_LIMIT_LEVEL_3(300),
    EMPLOYEE_LIMIT_LEVEL_4(5000);

    private Integer employeeLimit;
    EmployeeLimitLevel(Integer employeeLimit) {
        this.employeeLimit = employeeLimit;
    }
    public Integer getEmployeeLimit() {
        return employeeLimit;
    }


}
