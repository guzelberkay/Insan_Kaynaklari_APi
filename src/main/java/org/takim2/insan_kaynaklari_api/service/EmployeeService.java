package org.takim2.insan_kaynaklari_api.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.takim2.insan_kaynaklari_api.dto.request.EmployeeRequestDto;
import org.takim2.insan_kaynaklari_api.dto.response.EmployeeNameAndIdResponseDTO;
import org.takim2.insan_kaynaklari_api.dto.response.EmployeeResponseDto;
import org.takim2.insan_kaynaklari_api.entity.CompanyManager;
import org.takim2.insan_kaynaklari_api.entity.Employee;
import org.takim2.insan_kaynaklari_api.entity.User;
import org.takim2.insan_kaynaklari_api.entity.Company;
import org.takim2.insan_kaynaklari_api.entity.enums.UserRole;

import org.takim2.insan_kaynaklari_api.Vw.EmployeeView;
import org.takim2.insan_kaynaklari_api.dto.request.GetEmployeeRequestDTO;


import org.takim2.insan_kaynaklari_api.exception.ErrorType;
import org.takim2.insan_kaynaklari_api.exception.HumanResourcesAppException;
import org.takim2.insan_kaynaklari_api.mapper.EmployeeMapper;
import org.takim2.insan_kaynaklari_api.repository.CompanyManagerRepository;
import org.takim2.insan_kaynaklari_api.repository.CompanyRepository;
import org.takim2.insan_kaynaklari_api.repository.EmployeeRepository;

import org.takim2.insan_kaynaklari_api.repository.UserRepository;
import org.takim2.insan_kaynaklari_api.util.JwtTokenManager;

import java.util.ArrayList;
import java.util.List;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final JwtTokenManager jwtTokenManager;
    private final EmployeeMapper employeeMapper;
    private final CompanyRepository companyRepository;
    private final CompanyManagerRepository companyManagerRepository;

    public void addEmployee(EmployeeRequestDto dto) {
        User user;

        if (dto.getUserId() != null) {
            user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new HumanResourcesAppException(ErrorType.USER_NOT_FOUND));
        } else {
            user = new User();
            user.setFirstName(dto.getFirstName());
            user.setLastName(dto.getLastName());
            user.setEmail(dto.getEmail());
            user.setPassword(dto.getPassword()); // Parola şifreleme işlemi gerekecek
            user.setUserRole(UserRole.EMPLOYEE); // Çalışan rolü atanıyor
            user = userRepository.save(user);
        }

        Employee employee = EmployeeMapper.INSTANCE.toEmployee(dto);
        employee.setUser(user);
        employee.setSalary(dto.getSalary());
        employee.setActive(dto.isActive());
        employeeRepository.save(employee);
    }

    public void updateEmployee(Long userId, EmployeeRequestDto dto) {
        // Önce ilgili User nesnesini buluyoruz.
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new HumanResourcesAppException(ErrorType.USER_NOT_FOUND));

        // Kullanıcının bilgilerini güncelliyoruz.
        if (dto.getFirstName() != null) {
            user.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            user.setLastName(dto.getLastName());
        }
        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }
        if (dto.getPassword() != null) {
            user.setPassword(dto.getPassword()); // Parola şifreleme işlemi gerekecek
        }
        userRepository.save(user);

        // Sonra bu User'a ait Employee kaydını buluyoruz.
        Employee employee = employeeRepository.findByUser(user)
                .orElseThrow(() -> new HumanResourcesAppException(ErrorType.EMPLOYEE_NOT_FOUND));

        // Employee'yi güncelliyoruz.
        if (dto.getHireDate() != null) {
            employee.setHireDate(Long.valueOf(dto.getHireDate()));
        }
        if (dto.getBirthDate() != null) {
            employee.setBirthDate(Long.valueOf(dto.getBirthDate()));
        }
        if (dto.getAnnualLeave() != null) {
            employee.setAnnualLeave(dto.getAnnualLeave());
        }
        if (dto.getSalary() != null) {
            employee.setSalary(dto.getSalary());
        }
        employee.setActive(dto.isActive());

        // Şirket bilgisi güncellemesi
        if (dto.getCompany() != null) {
            Company company = companyRepository.findById(dto.getCompany())
                    .orElseThrow(() -> new HumanResourcesAppException(ErrorType.COMPANY_NOT_FOUND));
            employee.setCompany(company);
        }

        employeeRepository.save(employee);
    }



    public void deleteEmployee(Long userId) {
        // userId'ye göre User'ı bul
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new HumanResourcesAppException(ErrorType.USER_NOT_FOUND));

        // Bulunan User'a göre Employee'yi bul
        Employee employee = employeeRepository.findByUser(user)
                .orElseThrow(() -> new HumanResourcesAppException(ErrorType.EMPLOYEE_NOT_FOUND));

        // Employee'yi sil®
        employeeRepository.delete(employee);

    }


    public List<EmployeeResponseDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(employee -> EmployeeResponseDto.builder()
                        .user(employee.getUser().getId())
                        .firstName(employee.getUser().getFirstName())
                        .lastName(employee.getUser().getLastName())
                        .email(employee.getUser().getEmail())
                        .hireDate(employee.getHireDate().toString())
                        .birthDate(employee.getBirthDate().toString())
                        .annualLeave(employee.getAnnualLeave())
                        .isActive(employee.isActive())
                        .company(employee.getCompany().getId())
                        .build())
                .collect(Collectors.toList());
    }


    public void activateEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new HumanResourcesAppException(ErrorType.EMPLOYEE_NOT_FOUND));
        employee.setActive(true);
        employeeRepository.save(employee);
    }

    public void deactivateEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new HumanResourcesAppException(ErrorType.EMPLOYEE_NOT_FOUND));
        employee.setActive(false);
        employeeRepository.save(employee);
    }

    public List<EmployeeNameAndIdResponseDTO> getEmployeesByCompanyId(Long companyId) {
        List<EmployeeView> employeeViewList = employeeRepository.findAllBycompanyId(companyId);
        if (employeeViewList.isEmpty()) {
            throw new HumanResourcesAppException(ErrorType.USER_NOT_FOUND); //TODO Employee not found olacak!
        }
        List<EmployeeNameAndIdResponseDTO> employeeResponseDTOList = new ArrayList<>();
        for (EmployeeView employeeView : employeeViewList) {
            employeeResponseDTOList.add(employeeMapper.employeeViewToEmployeeNameAndIdResponseDTO(employeeView));
        }
        return employeeResponseDTOList;
    }

    public Employee findEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow(() -> new HumanResourcesAppException(ErrorType.USER_NOT_FOUND));

    }

    public List<EmployeeResponseDto> getEmployeesByCompanyManagerId(Long userId) {
        // Önce userId'ye göre CompanyManager'ı bul
        CompanyManager companyManager = companyManagerRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Company Manager not found"));

        // CompanyManager'ın yönettiği tüm şirketleri bulun
        List<Long> companyIds = companyRepository.findAllByCompanyManager(companyManager)
                .stream()
                .map(Company::getId)
                .collect(Collectors.toList());

        // Bu şirketlere bağlı tüm çalışanları bulun
        List<Employee> employees = employeeRepository.findAllByCompanyIdIn(companyIds);

        return employees.stream()
                .map(employee -> EmployeeResponseDto.builder()
                        .user(employee.getUser().getId())
                        .firstName(employee.getUser().getFirstName())
                        .lastName(employee.getUser().getLastName())
                        .email(employee.getUser().getEmail())
                        .hireDate(employee.getHireDate().toString())
                        .birthDate(employee.getBirthDate().toString())
                        .annualLeave(employee.getAnnualLeave())
                        .isActive(employee.isActive())
                        .company(employee.getCompany().getId())
                        .build())
                .collect(Collectors.toList());
    }
    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    public Employee findEmployeeByUserId(Long userId) {
        return employeeRepository.findByUserId(userId).orElseThrow(() -> new HumanResourcesAppException(ErrorType.USER_NOT_FOUND));
    }
}

