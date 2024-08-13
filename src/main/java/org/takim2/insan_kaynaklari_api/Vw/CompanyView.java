package org.takim2.insan_kaynaklari_api.Vw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CompanyView {
    private Long companyId;
    private String companyName;
}
