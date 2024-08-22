package org.takim2.insan_kaynaklari_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.takim2.insan_kaynaklari_api.dto.request.AssetRequestDto;
import org.takim2.insan_kaynaklari_api.dto.request.AssetVerificationRequestDto;
import org.takim2.insan_kaynaklari_api.dto.response.AssetResponseDto;
import org.takim2.insan_kaynaklari_api.dto.response.ResponseDTO;
import org.takim2.insan_kaynaklari_api.service.AssetService;

import java.util.List;

@RestController
@RequestMapping("/assets")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;

    @PostMapping("/assignAsset")
    public ResponseEntity<ResponseDTO<AssetResponseDto>> assignAsset(@RequestBody AssetRequestDto assetRequestDto) {
        AssetResponseDto assetResponseDto = assetService.assignAsset(assetRequestDto);
        return ResponseEntity.ok(ResponseDTO.<AssetResponseDto>builder()
                .code(200)
                .message("Zimmet eşyası başarıyla atandı")
                .data(assetResponseDto)
                .build());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseDTO<List<AssetResponseDto>>> getAssetsByUserId(@PathVariable Long userId) {
        List<AssetResponseDto> assets = assetService.getAssetsByUserId(userId);
        return ResponseEntity.ok(ResponseDTO.<List<AssetResponseDto>>builder()
                .code(200)
                .message("Kullanıcının zimmetli eşyaları getirildi")
                .data(assets)
                .build());
    }

    @PutMapping("/{assetId}/verify")
    public ResponseEntity<ResponseDTO<Boolean>> verifyAsset(@RequestBody AssetVerificationRequestDto assetVerificationRequestDto) {
        assetService.verifyAsset(assetVerificationRequestDto);
        return ResponseEntity.ok(ResponseDTO.<Boolean>builder()
                .code(200)
                .message("Zimmet eşyası doğrulandı")
                .data(true)
                .build());
    }

    @PutMapping("/{assetId}/reject")
    public ResponseEntity<ResponseDTO<Boolean>> rejectAsset(@PathVariable Long assetId, @RequestBody String note) {
        assetService.rejectAsset(assetId, note);
        return ResponseEntity.ok(ResponseDTO.<Boolean>builder()
                .code(200)
                .message("Zimmet reddedildi ve not eklendi")
                .data(true)
                .build());
    }

}
