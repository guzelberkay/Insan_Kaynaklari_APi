package org.takim2.insan_kaynaklari_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.takim2.insan_kaynaklari_api.dto.request.ShiftRequestDto;
import org.takim2.insan_kaynaklari_api.dto.response.EmployeeResponseDto;
import org.takim2.insan_kaynaklari_api.dto.response.EmployeeShiftDto;
import org.takim2.insan_kaynaklari_api.dto.response.ShiftResponseDto;
import org.takim2.insan_kaynaklari_api.entity.Employee;
import org.takim2.insan_kaynaklari_api.entity.Shift;
import org.takim2.insan_kaynaklari_api.repository.ShiftRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShiftService {
    private final ShiftRepository shiftRepository;
    private final EmployeeService employeeService;



    public void assignShifts(ShiftRequestDto dto) {
        Employee employee = employeeService.findEmployeeById(dto.getEmployeeId());
        Shift shift = Shift.builder()
                .employee(Collections.singletonList(employee))
                .startDate(dto.getStartDate().toString())
                .endDate(dto.getEndDate().toString())
                .startTime(dto.getStartTime().toString())
                .endTime(dto.getEndTime().toString())
                .build();
        shiftRepository.save(shift);
    }

    public List<ShiftResponseDto> getAllShifts() {
        List<Shift> allShifts = shiftRepository.findAll();

        return allShifts.stream()
                .map(shift -> ShiftResponseDto.builder()
                        .startDate(shift.getStartDate())
                        .endDate(shift.getEndDate())
                        .startTime(shift.getStartTime())
                        .endTime(shift.getEndTime())
                        .employees(shift.getEmployee().stream()
                                .map(emp -> new EmployeeShiftDto(emp.getId(), emp.getUser().getFirstName(), emp.getUser().getLastName()))
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());

    }
}
