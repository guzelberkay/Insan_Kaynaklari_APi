package org.takim2.insan_kaynaklari_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.takim2.insan_kaynaklari_api.Vw.MyShiftsView;
import org.takim2.insan_kaynaklari_api.dto.request.ShiftRequestDto;
import org.takim2.insan_kaynaklari_api.dto.response.*;
import org.takim2.insan_kaynaklari_api.entity.Employee;
import org.takim2.insan_kaynaklari_api.entity.Shift;
import org.takim2.insan_kaynaklari_api.exception.ErrorType;
import org.takim2.insan_kaynaklari_api.exception.HumanResourcesAppException;
import org.takim2.insan_kaynaklari_api.mapper.ShiftMapper;
import org.takim2.insan_kaynaklari_api.repository.ShiftRepository;
import org.takim2.insan_kaynaklari_api.util.JwtTokenManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShiftService {
    private final ShiftRepository shiftRepository;
    private final EmployeeService employeeService;
    private final JwtTokenManager jwtTokenManager;



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
    public List<MyShiftsResponseDto> getMyShifts(String jwtToken) {
        Long userId = jwtTokenManager.getUserIdFromToken(jwtToken).orElseThrow(() -> new HumanResourcesAppException(ErrorType.USER_NOT_FOUND));
        Employee employee = employeeService.findEmployeeByUserId(userId);

        List<MyShiftsResponseDto> myShiftsResponseDTOList = new ArrayList<>();

        List<MyShiftsView> myShiftViewByEmployee = shiftRepository.findAllMyShiftViewByEmployee(employee);
        if(myShiftViewByEmployee.isEmpty()){
            throw new HumanResourcesAppException(ErrorType.DATA_NOT_FOUND);
        }

        myShiftViewByEmployee.forEach(shiftView -> {
            myShiftsResponseDTOList.add(ShiftMapper.INSTANCE.myShiftViewToMyShiftsResponseDTO(shiftView));
        });


        return myShiftsResponseDTOList;
    }



}
