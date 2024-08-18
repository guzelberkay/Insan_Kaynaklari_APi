package org.takim2.insan_kaynaklari_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import org.takim2.insan_kaynaklari_api.dto.request.EmployeeRequestDto;
import org.takim2.insan_kaynaklari_api.dto.response.EmployeeNameAndIdResponseDTO;
import org.takim2.insan_kaynaklari_api.entity.Company;
import org.takim2.insan_kaynaklari_api.entity.Employee;

import org.takim2.insan_kaynaklari_api.Vw.EmployeeView;



@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    Employee toEmployee(EmployeeRequestDto dto);

    EmployeeNameAndIdResponseDTO employeeViewToEmployeeNameAndIdResponseDTO(EmployeeView employeeView);

    default Company map(Long value) {
        if (value == null) {
            return null;
        }
        Company company = new Company();
        company.setId(value);
        return company;
    }
}

