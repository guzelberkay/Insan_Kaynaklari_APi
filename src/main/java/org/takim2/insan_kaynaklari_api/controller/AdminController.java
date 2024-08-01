package org.takim2.insan_kaynaklari_api.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.takim2.insan_kaynaklari_api.dto.request.SendActivationRequestDTO;
import org.takim2.insan_kaynaklari_api.dto.response.PendingCompaniesResponseDTO;
import org.takim2.insan_kaynaklari_api.dto.response.ResponseDTO;
import org.takim2.insan_kaynaklari_api.service.AdminService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;





    @GetMapping("/get-pending-user")
    public ResponseEntity<ResponseDTO<List<PendingCompaniesResponseDTO>>> getPendingUsers(@RequestParam String token){
        return ResponseEntity.ok(ResponseDTO.<List<PendingCompaniesResponseDTO>>builder().code(200).data(adminService.getPendingUsers(token)).message("Pending User Listesi Gönderildi.").build());
    }




    @PostMapping("/send-activation-mail")
    public ResponseEntity<ResponseDTO<Boolean>> sendActivationMail(@RequestBody SendActivationRequestDTO sendActivationRequestDTO){
        adminService.sendActivationMail(sendActivationRequestDTO);
        return ResponseEntity.ok(ResponseDTO.<Boolean>builder().code(200).message("Aktivasyon Maili Gönderildi").build());
    }

    @GetMapping("/activate-account")
    public ResponseEntity<ResponseDTO<Boolean>> activateAccount(@RequestParam String token){
        adminService.activateAccount(token);
        return ResponseEntity.ok(ResponseDTO.<Boolean>builder().code(200).message("Hesap Aktive Edildi").build());
    }

}
