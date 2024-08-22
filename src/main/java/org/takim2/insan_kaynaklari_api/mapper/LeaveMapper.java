package org.takim2.insan_kaynaklari_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.takim2.insan_kaynaklari_api.Vw.CompanyView;
import org.takim2.insan_kaynaklari_api.Vw.PendingLeaveView;
import org.takim2.insan_kaynaklari_api.dto.request.RegisterRequestDto;
import org.takim2.insan_kaynaklari_api.dto.response.CompaniesForLeaveDTO;
import org.takim2.insan_kaynaklari_api.dto.response.PendingLeaveResponseDTO;
import org.takim2.insan_kaynaklari_api.entity.Company;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LeaveMapper {
    LeaveMapper INSTANCE = Mappers.getMapper(LeaveMapper.class);

    PendingLeaveResponseDTO pendingViewToPendingLeaveResponseDTO(PendingLeaveView pendingLeaveView);

}
