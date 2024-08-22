package org.takim2.insan_kaynaklari_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.takim2.insan_kaynaklari_api.dto.request.LeaveRequestDTO;
import org.takim2.insan_kaynaklari_api.dto.request.LeaveSaveRequestDTO;
import org.takim2.insan_kaynaklari_api.dto.response.ResponseDTO;
import org.takim2.insan_kaynaklari_api.service.Leaveservice;

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
}
