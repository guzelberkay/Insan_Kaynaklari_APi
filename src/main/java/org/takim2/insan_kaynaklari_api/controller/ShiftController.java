package org.takim2.insan_kaynaklari_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.takim2.insan_kaynaklari_api.dto.request.LeaveSaveRequestDTO;
import org.takim2.insan_kaynaklari_api.dto.request.ShiftRequestDto;
import org.takim2.insan_kaynaklari_api.dto.response.MyExpensesResponseDTO;
import org.takim2.insan_kaynaklari_api.dto.response.MyShiftsResponseDto;
import org.takim2.insan_kaynaklari_api.dto.response.ResponseDTO;
import org.takim2.insan_kaynaklari_api.dto.response.ShiftResponseDto;
import org.takim2.insan_kaynaklari_api.entity.Shift;
import org.takim2.insan_kaynaklari_api.service.ShiftService;

import java.util.List;

@RestController
@RequestMapping("/shift")
@RequiredArgsConstructor
public class ShiftController {


    private final ShiftService shiftService;

    @PostMapping("/assignShifts")
    public ResponseEntity<ResponseDTO<Boolean>> assignShifts(@RequestBody ShiftRequestDto dto) {
        shiftService.assignShifts(dto);
        return ResponseEntity.ok(ResponseDTO.<Boolean>builder().code(200).message("Vardiya Başarıyla Tanımlandı").data(Boolean.TRUE).build());
    }

    @GetMapping("/getAllShifts")
    public ResponseEntity<ResponseDTO<List<ShiftResponseDto>>> getAllShifts() {
        return ResponseEntity.ok(ResponseDTO.<List<ShiftResponseDto>>builder().code(200).message("Tüm Vardiyalar Getirildi").data(shiftService.getAllShifts()).build());
    }
    @GetMapping("/getMyShifts")
    public ResponseEntity<ResponseDTO<List<MyShiftsResponseDto>>> getMyShifts(@RequestHeader("Authorization") String token) {
        String jwtToken = token.replace("Bearer ", "");
        return ResponseEntity.ok(ResponseDTO.<List<MyShiftsResponseDto>>builder().code(200).message("Vardiya Listesi Gönderildi").data(shiftService.getMyShifts(jwtToken)).build());
    }

}
