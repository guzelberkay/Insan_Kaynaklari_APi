package org.takim2.insan_kaynaklari_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.takim2.insan_kaynaklari_api.dto.request.EmployeeRequestDto;
import org.takim2.insan_kaynaklari_api.dto.response.EmployeeNameAndIdResponseDTO;
import org.takim2.insan_kaynaklari_api.dto.response.EmployeeResponseDto;
import org.takim2.insan_kaynaklari_api.entity.Employee;
import org.takim2.insan_kaynaklari_api.entity.User;
import org.takim2.insan_kaynaklari_api.entity.enums.UserRole;

import org.takim2.insan_kaynaklari_api.Vw.EmployeeView;
import org.takim2.insan_kaynaklari_api.dto.request.GetEmployeeRequestDTO;


import org.takim2.insan_kaynaklari_api.exception.ErrorType;
import org.takim2.insan_kaynaklari_api.exception.HumanResourcesAppException;
import org.takim2.insan_kaynaklari_api.mapper.EmployeeMapper;
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
        employeeRepository.save(employee);
    }

    public void updateEmployee(Long userId, EmployeeRequestDto dto) {
        // Önce ilgili User nesnesini buluyoruz.
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new HumanResourcesAppException(ErrorType.USER_NOT_FOUND));

        // Sonra bu User'a ait Employee kaydını buluyoruz.
        Employee employee = employeeRepository.findByUser(user)
                .orElseThrow(() -> new HumanResourcesAppException(ErrorType.EMPLOYEE_NOT_FOUND));

        // Employee'yi güncelliyoruz.
        employee.setHireDate(Long.valueOf(dto.getHireDate()));
        employee.setBirthDate(Long.valueOf(dto.getBirthDate()));
        employee.setAnnualLeave(dto.getAnnualLeave());
        employee.setActive(dto.isActive());
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

    public List<EmployeeNameAndIdResponseDTO> getEmployeesByCompanyId(GetEmployeeRequestDTO getEmployeeRequestDTO) {
        List<EmployeeView> employeeViewList = employeeRepository.findAllBycompanyId(getEmployeeRequestDTO.getCompanyId());
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
}

