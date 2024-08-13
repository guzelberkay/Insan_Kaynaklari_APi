package org.takim2.insan_kaynaklari_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.takim2.insan_kaynaklari_api.Vw.EmployeeView;
import org.takim2.insan_kaynaklari_api.dto.request.GetEmployeeRequestDTO;
import org.takim2.insan_kaynaklari_api.dto.response.EmployeeResponseDTO;
import org.takim2.insan_kaynaklari_api.entity.Employee;
import org.takim2.insan_kaynaklari_api.exception.ErrorType;
import org.takim2.insan_kaynaklari_api.exception.HumanResourcesAppException;
import org.takim2.insan_kaynaklari_api.mapper.EmployeeMapper;
import org.takim2.insan_kaynaklari_api.repository.EmployeeRepository;
import org.takim2.insan_kaynaklari_api.util.JwtTokenManager;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final JwtTokenManager jwtTokenManager;
    private final EmployeeMapper employeeMapper;

    public List<EmployeeResponseDTO> getEmployeesByCompanyId(GetEmployeeRequestDTO getEmployeeRequestDTO) {
        List<EmployeeView> employeeViewList = employeeRepository.findAllBycompanyId(getEmployeeRequestDTO.getCompanyId());
        if (employeeViewList.isEmpty()) {
            throw new HumanResourcesAppException(ErrorType.USER_NOT_FOUND); //TODO Employee not found olacak!
        }
        List<EmployeeResponseDTO> employeeResponseDTOList = new ArrayList<>();
        for (EmployeeView employeeView : employeeViewList) {
            employeeResponseDTOList.add(employeeMapper.employeeViewToEmployeeResponseDTO(employeeView));
        }
        return employeeResponseDTOList;
    }
    public Employee findEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow(()-> new HumanResourcesAppException(ErrorType.USER_NOT_FOUND));
    }
}
