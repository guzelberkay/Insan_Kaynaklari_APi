package org.takim2.insan_kaynaklari_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.takim2.insan_kaynaklari_api.Vw.MyShiftsView;
import org.takim2.insan_kaynaklari_api.dto.response.MyShiftsResponseDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShiftMapper {
    ShiftMapper INSTANCE = Mappers.getMapper(ShiftMapper.class);

    MyShiftsResponseDto myShiftViewToMyShiftsResponseDTO(MyShiftsView myShiftsViev);
}
