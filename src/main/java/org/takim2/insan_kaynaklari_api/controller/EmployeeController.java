package org.takim2.insan_kaynaklari_api.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.takim2.insan_kaynaklari_api.dto.request.EmployeeRequestDto;
import org.takim2.insan_kaynaklari_api.dto.response.EmployeeNameAndIdResponseDTO;
import org.takim2.insan_kaynaklari_api.dto.response.EmployeeResponseDto;


import org.takim2.insan_kaynaklari_api.dto.request.GetEmployeeRequestDTO;


import org.takim2.insan_kaynaklari_api.dto.response.ResponseDTO;
import org.takim2.insan_kaynaklari_api.service.EmployeeService;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/add")
    @CrossOrigin("*")
    public ResponseEntity<ResponseDTO<Boolean>> addEmployee(@RequestBody @Valid EmployeeRequestDto dto) {
        employeeService.addEmployee(dto);
        return ResponseEntity.ok(ResponseDTO.<Boolean>builder().code(200).message("Çalışan eklendi").data(true).build());
    }

    @PutMapping("/update/{userId}")
    @CrossOrigin("*")
    public ResponseEntity<ResponseDTO<Boolean>> updateEmployee(@PathVariable Long userId, @RequestBody @Valid EmployeeRequestDto dto) {
        employeeService.updateEmployee(userId, dto);
        return ResponseEntity.ok(ResponseDTO.<Boolean>builder().code(200).message("Çalışan güncellendi").data(true).build());
    }

    @DeleteMapping("/delete/{userId}")
    @CrossOrigin("*")
    public ResponseEntity<ResponseDTO<Boolean>> deleteEmployee(@PathVariable Long userId) {
        employeeService.deleteEmployee(userId);
        return ResponseEntity.ok(ResponseDTO.<Boolean>builder().code(200).message("Çalışan silindi").data(true).build());
    }

    @GetMapping("/get-employees")
    @CrossOrigin("*")
    public ResponseEntity<ResponseDTO<List<EmployeeResponseDto>>> getEmployees() {
        List<EmployeeResponseDto> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(ResponseDTO.<List<EmployeeResponseDto>>builder()
                .code(200)
                .message("Çalışanlar listelendi")
                .data(employees)
                .build());
    }

    @PutMapping("/activate/{id}")
    @CrossOrigin("*")
    public ResponseEntity<ResponseDTO<Boolean>> activateEmployee(@PathVariable Long id) {
        employeeService.activateEmployee(id);
        return ResponseEntity.ok(ResponseDTO.<Boolean>builder().code(200).message("Çalışan aktifleştirildi").data(true).build());
    }

    @PutMapping("/deactivate/{id}")
    @CrossOrigin("*")
    public ResponseEntity<ResponseDTO<Boolean>> deactivateEmployee(@PathVariable Long id) {
        employeeService.deactivateEmployee(id);
        return ResponseEntity.ok(ResponseDTO.<Boolean>builder().code(200).message("Çalışan pasifleştirildi").data(true).build());
    }


    @GetMapping("/get-employee-by-company-id")
    public ResponseEntity<ResponseDTO<List<EmployeeNameAndIdResponseDTO>>> getEmployeesByCompanyId(@RequestBody GetEmployeeRequestDTO getEmployeeRequestDTO) {
        return ResponseEntity.ok(ResponseDTO.<List<EmployeeNameAndIdResponseDTO>>builder().code(200).message("Çalışan listesi gönderildi").data(employeeService.getEmployeesByCompanyId(getEmployeeRequestDTO)).build());
    }
}

