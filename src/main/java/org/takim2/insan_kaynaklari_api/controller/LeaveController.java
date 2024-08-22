package org.takim2.insan_kaynaklari_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.takim2.insan_kaynaklari_api.dto.request.LeaveRequestDTO;
import org.takim2.insan_kaynaklari_api.dto.request.LeaveSaveRequestDTO;
import org.takim2.insan_kaynaklari_api.dto.request.UpdateLeaveStatusRequestDTO;
import org.takim2.insan_kaynaklari_api.dto.response.PendingLeaveResponseDTO;
import org.takim2.insan_kaynaklari_api.dto.response.ResponseDTO;
import org.takim2.insan_kaynaklari_api.service.Leaveservice;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/leave")
public class LeaveController {
    private final Leaveservice leaveservice;


    @PostMapping("/save-leave")
    public ResponseEntity<ResponseDTO<Boolean>> saveLeave(@RequestBody LeaveSaveRequestDTO leaveSaveRequestDTO) {
        leaveservice.saveLeave(leaveSaveRequestDTO);
        return ResponseEntity.ok(ResponseDTO.<Boolean>builder().code(200).message("İzin Başarıyla Tanımlandı").data(Boolean.TRUE).build());
    }

    @PostMapping("/leave-request")
    public ResponseEntity<ResponseDTO<Boolean>> leaveRequest(@RequestBody LeaveRequestDTO leaveRequestDTO) {
        leaveservice.leaveRequest(leaveRequestDTO);
        return ResponseEntity.ok(ResponseDTO.<Boolean>builder().code(200).message("İzin isteği gönderildi").build());
    }

    @GetMapping("/get-pending-leaves/{companyId}")
    public ResponseEntity<ResponseDTO<List<PendingLeaveResponseDTO>>> getPendingLeaves(@PathVariable Long companyId) {
        return ResponseEntity.ok(ResponseDTO.<List<PendingLeaveResponseDTO>>builder().code(200).message("Onay bekleyen izinler gönderildi").data(leaveservice.getPendingLeaves(companyId)).build());
    }

    @PutMapping("/update-leave-status")
    public ResponseEntity<ResponseDTO<Boolean>> updateLeaveStatus(@RequestBody UpdateLeaveStatusRequestDTO updateLeaveStatusRequestDTO) {
        leaveservice.updateLeaveStatus(updateLeaveStatusRequestDTO);
        return ResponseEntity.ok(ResponseDTO.<Boolean>builder().code(200).message("leave status updated").build());
    }



}
