package org.takim2.insan_kaynaklari_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ResponseDTO <T> {
    private Integer code;
    private String message;
    private T data;
}
