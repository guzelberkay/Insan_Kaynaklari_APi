package org.takim2.insan_kaynaklari_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AssetResponseDto {
    private Long id;
    private Long userId;
    private String serialNumber;
    private Long assignedDate;
    private boolean isReturned;
    private String verificationStatus;
    private String note;

}

