package org.takim2.insan_kaynaklari_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.takim2.insan_kaynaklari_api.dto.request.RegisterRequestDto;

import org.takim2.insan_kaynaklari_api.entity.Company;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompanyMapper {
    CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);

    Company toCompany(RegisterRequestDto dto);

}
