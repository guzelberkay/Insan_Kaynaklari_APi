package org.takim2.insan_kaynaklari_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.takim2.insan_kaynaklari_api.dto.request.LeaveSaveRequestDTO;
import org.takim2.insan_kaynaklari_api.entity.Leave;
import org.takim2.insan_kaynaklari_api.entity.enums.LeaveStatus;
import org.takim2.insan_kaynaklari_api.repository.LeaveRepository;

@Service
@RequiredArgsConstructor
public class Leaveservice {
    private final LeaveRepository leaveRepository;
    private final EmployeeService employeeService;


    public void saveLeave(LeaveSaveRequestDTO leaveSaveRequestDTO) {
        leaveRepository.save(Leave.builder()
                        .employee(employeeService.findEmployeeById(leaveSaveRequestDTO.getEmployeeId()))
                        .leaveType(leaveSaveRequestDTO.getLeaveType())
                        .leaveStatus(LeaveStatus.ACCEPTED)
                        .startDate(leaveSaveRequestDTO.getStartDate())
                        .endDate(leaveSaveRequestDTO.getEndDate())
                        .description(leaveSaveRequestDTO.getDescription())
                .build());
    }
}
