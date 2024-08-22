package org.takim2.insan_kaynaklari_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.takim2.insan_kaynaklari_api.dto.request.AssetRequestDto;
import org.takim2.insan_kaynaklari_api.dto.request.AssetVerificationRequestDto;
import org.takim2.insan_kaynaklari_api.dto.response.AssetResponseDto;
import org.takim2.insan_kaynaklari_api.entity.Asset;
import org.takim2.insan_kaynaklari_api.entity.User;
import org.takim2.insan_kaynaklari_api.exception.ErrorType;
import org.takim2.insan_kaynaklari_api.exception.HumanResourcesAppException;
import org.takim2.insan_kaynaklari_api.repository.AssetRepository;
import org.takim2.insan_kaynaklari_api.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AssetService {

    private final AssetRepository assetRepository;
    private final UserRepository userRepository;

    public List<AssetResponseDto> getAssetsByUserId(Long userId) {
        List<Asset> assets = assetRepository.findAllByUserId(userId);
        return assets.stream().map(this::convertToResponseDto).collect(Collectors.toList());
    }

    public AssetResponseDto assignAsset(AssetRequestDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new HumanResourcesAppException(ErrorType.USER_NOT_FOUND));

        Asset asset = Asset.builder()
                .user(user)
                .serialNumber(dto.getSerialNumber())
                .assetName(dto.getAssetName())
                .assignedDate(dto.getAssignedDate())
                .isReturned(false)
                .build();

        return convertToResponseDto(assetRepository.save(asset));
    }

    public void verifyAsset(AssetVerificationRequestDto dto) {
        Asset asset = assetRepository.findById(dto.getAssetId())
                .orElseThrow(() -> new HumanResourcesAppException(ErrorType.ASSET_NOT_FOUND));

        asset.setVerificationStatus(dto.getVerificationStatus());
        asset.setNote(dto.getNote());
        assetRepository.save(asset);
    }

    public void rejectAsset(Long assetId, String note) {
        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() -> new HumanResourcesAppException(ErrorType.ASSET_NOT_FOUND));
        asset.setVerificationStatus("REJECTED");
        asset.setNote(note);
        assetRepository.save(asset);
    }

    private AssetResponseDto convertToResponseDto(Asset asset) {
        return AssetResponseDto.builder()
                .id(asset.getId())
                .userId(asset.getUser().getId())
                .serialNumber(asset.getSerialNumber())
                .assetName(asset.getAssetName())
                .assignedDate(asset.getAssignedDate())
                .isReturned(asset.isReturned())
                .verificationStatus(asset.getVerificationStatus())
                .note(asset.getNote())
                .build();
    }
}
