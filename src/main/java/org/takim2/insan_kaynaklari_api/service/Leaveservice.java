package org.takim2.insan_kaynaklari_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.takim2.insan_kaynaklari_api.Vw.PendingLeaveView;
import org.takim2.insan_kaynaklari_api.dto.request.LeaveRequestDTO;
import org.takim2.insan_kaynaklari_api.dto.request.LeaveSaveRequestDTO;
import org.takim2.insan_kaynaklari_api.dto.request.UpdateLeaveStatusRequestDTO;
import org.takim2.insan_kaynaklari_api.dto.response.PendingLeaveResponseDTO;
import org.takim2.insan_kaynaklari_api.entity.Employee;
import org.takim2.insan_kaynaklari_api.entity.Leave;
import org.takim2.insan_kaynaklari_api.entity.enums.LeaveStatus;
import org.takim2.insan_kaynaklari_api.exception.HumanResourcesAppException;
import org.takim2.insan_kaynaklari_api.mapper.LeaveMapper;
import org.takim2.insan_kaynaklari_api.repository.LeaveRepository;
import org.takim2.insan_kaynaklari_api.util.JwtTokenManager;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.takim2.insan_kaynaklari_api.exception.ErrorType.*;

@Service
@RequiredArgsConstructor
public class Leaveservice {
    private final LeaveRepository leaveRepository;
    private final EmployeeService employeeService;
    private final JwtTokenManager jwtTokenManager;


    public void saveLeave(LeaveSaveRequestDTO leaveSaveRequestDTO) {
        Employee employee = employeeService.findEmployeeById(leaveSaveRequestDTO.getEmployeeId());
        checkEmployeeLeaveDate(leaveSaveRequestDTO.getStartDate(), leaveSaveRequestDTO.getEndDate(), employee);
        leaveRepository.save(Leave.builder()
                        .employee(employee)
                        .leaveType(leaveSaveRequestDTO.getLeaveType())
                        .leaveStatus(LeaveStatus.ACCEPTED)
                        .startDate(leaveSaveRequestDTO.getStartDate())
                        .endDate(leaveSaveRequestDTO.getEndDate())
                        .description(leaveSaveRequestDTO.getDescription())
                .build());
    }
    private void checkEmployeeLeaveDate(Long startDate, Long endDate, Employee employee) {
        Instant instantStartDate = Instant.ofEpochSecond(startDate);
        Instant instantEndDate = Instant.ofEpochSecond(endDate);

        LocalDate localDateStart = instantStartDate.atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDateEnd = instantEndDate.atZone(ZoneId.systemDefault()).toLocalDate();

        int leaveDate = ((int) ChronoUnit.DAYS.between(localDateStart, localDateEnd));
        if(leaveDate > employee.getAnnualLeave()){
            throw new HumanResourcesAppException(NOT_ENOUGH_ANNUAL_LEAVE_DATE);
        }
        employee.setAnnualLeave((employee.getAnnualLeave() - leaveDate));
        employeeService.saveEmployee(employee);
    }

    public void leaveRequest(LeaveRequestDTO leaveRequestDTO) {
        Long userId = jwtTokenManager.getUserIdFromToken(leaveRequestDTO.getToken()).orElseThrow(() -> new HumanResourcesAppException(INVALID_TOKEN));
        Employee employee = employeeService.findEmployeeByUserId(userId);
        Instant instantStartDate = Instant.ofEpochSecond(leaveRequestDTO.getStartDate());
        Instant instantEndDate = Instant.ofEpochSecond(leaveRequestDTO.getEndDate());

        //TODO Yukarıyı ve burayı düzenlemek lazım
        LocalDate localDateStart = instantStartDate.atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDateEnd = instantEndDate.atZone(ZoneId.systemDefault()).toLocalDate();

        int leaveDate = ((int) ChronoUnit.DAYS.between(localDateStart, localDateEnd));
        if(leaveDate > employee.getAnnualLeave()){
            throw new HumanResourcesAppException(NOT_ENOUGH_ANNUAL_LEAVE_DATE);
        }

        leaveRepository.save(Leave.builder()
                        .startDate(leaveRequestDTO.getStartDate())
                        .leaveStatus(LeaveStatus.PENDING)
                        .leaveType(leaveRequestDTO.getLeaveType())
                        .description(leaveRequestDTO.getDescription())
                        .employee(employee)
                        .endDate(leaveRequestDTO.getEndDate())
                .build());
    }

    public List<PendingLeaveResponseDTO> getPendingLeaves(Long companyId) {
        List<PendingLeaveView> allPendingLeavesByCompanyId = leaveRepository.findAllPendingLeavesByCompanyId(companyId,LeaveStatus.PENDING);
        if(allPendingLeavesByCompanyId.isEmpty()){
            throw new HumanResourcesAppException(DATA_NOT_FOUND);
        }
        List<PendingLeaveResponseDTO> pendingLeaveResponseDTOList = new ArrayList<>();
        allPendingLeavesByCompanyId.forEach(pendingLeaveView -> {
            pendingLeaveResponseDTOList.add(LeaveMapper.INSTANCE.pendingViewToPendingLeaveResponseDTO(pendingLeaveView));
        });


        return pendingLeaveResponseDTOList;
    }

    public void updateLeaveStatus(UpdateLeaveStatusRequestDTO updateLeaveStatusRequestDTO) {
        Leave leave = leaveRepository.findById(updateLeaveStatusRequestDTO.getLeaveId()).orElseThrow(() -> new HumanResourcesAppException(DATA_NOT_FOUND));
        if(updateLeaveStatusRequestDTO.getIsApproved()){
            leave.setLeaveStatus(LeaveStatus.ACCEPTED);
            Employee employee = leave.getEmployee();
            Instant instantStartDate = Instant.ofEpochSecond(leave.getStartDate());
            Instant instantEndDate = Instant.ofEpochSecond(leave.getEndDate());
            LocalDate localDateStart = instantStartDate.atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate localDateEnd = instantEndDate.atZone(ZoneId.systemDefault()).toLocalDate();

            int leaveDate = ((int) ChronoUnit.DAYS.between(localDateStart, localDateEnd));
            if(leaveDate > employee.getAnnualLeave()){
                throw new HumanResourcesAppException(NOT_ENOUGH_ANNUAL_LEAVE_DATE);
            }
            employee.setAnnualLeave((employee.getAnnualLeave() - leaveDate));
            employeeService.saveEmployee(employee);
        }else{
            leave.setLeaveStatus(LeaveStatus.REJECTED);
        }
        leaveRepository.save(leave);
    }
}
