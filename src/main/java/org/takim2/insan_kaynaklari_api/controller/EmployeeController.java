package org.takim2.insan_kaynaklari_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.takim2.insan_kaynaklari_api.dto.request.GetEmployeeRequestDTO;
import org.takim2.insan_kaynaklari_api.dto.response.EmployeeResponseDTO;
import org.takim2.insan_kaynaklari_api.dto.response.ResponseDTO;
import org.takim2.insan_kaynaklari_api.service.EmployeeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;



    @GetMapping("/get-employee-by-company-id")
    public ResponseEntity<ResponseDTO<List<EmployeeResponseDTO>>> getEmployeesByCompanyId(@RequestBody GetEmployeeRequestDTO getEmployeeRequestDTO) {
        return ResponseEntity.ok(ResponseDTO.<List<EmployeeResponseDTO>>builder().code(200).message("Çalışan listesi gönderildi").data(employeeService.getEmployeesByCompanyId(getEmployeeRequestDTO)).build());
    }

}
