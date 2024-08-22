package org.takim2.insan_kaynaklari_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.takim2.insan_kaynaklari_api.Vw.CompanyView;
import org.takim2.insan_kaynaklari_api.Vw.PassiveCompaniesView;
import org.takim2.insan_kaynaklari_api.dto.response.CompaniesForLeaveDTO;
import org.takim2.insan_kaynaklari_api.entity.Company;
import org.takim2.insan_kaynaklari_api.exception.HumanResourcesAppException;
import org.takim2.insan_kaynaklari_api.mapper.CompanyMapper;
import org.takim2.insan_kaynaklari_api.repository.CompanyRepository;
import org.takim2.insan_kaynaklari_api.util.JwtTokenManager;

import java.util.ArrayList;
import java.util.List;

import static org.takim2.insan_kaynaklari_api.exception.ErrorType.*;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final JwtTokenManager jwtTokenManager;

    public List<PassiveCompaniesView> getPendingCompanies(boolean isActive) {
        return companyRepository.findByIsActive(isActive);
    }

    public void updateCompanyStatus(Long companyId, boolean isActive) {
        Company company = companyRepository.findById(companyId).orElseThrow(()-> new HumanResourcesAppException(COMPANY_NOT_FOUND));
        company.setActive(isActive);
        companyRepository.save(company);
    }

    public List<CompaniesForLeaveDTO> getCompanies(String jwtToken) {
        Long userId = jwtTokenManager.getUserIdFromToken(jwtToken).orElseThrow(() -> new HumanResourcesAppException(USER_NOT_FOUND));
        List<CompanyView> companyManagersCompanies = companyRepository.findCompanyManagersCompanies(userId);
        if(companyManagersCompanies.isEmpty()) {
            throw new HumanResourcesAppException(COMPANY_NOT_FOUND);
        }

        List<CompaniesForLeaveDTO> companiesForLeaves = new ArrayList<>();
        companyManagersCompanies.forEach(companyView -> {
            companiesForLeaves.add(CompanyMapper.INSTANCE.companyViewToCompaniesForLeaveDTO(companyView));
        });
        return companiesForLeaves;

    }

    public Company findCompanyById(Long companyId) {
        return companyRepository.findById(companyId).orElseThrow(() -> new HumanResourcesAppException(COMPANY_NOT_FOUND));
    }
}

